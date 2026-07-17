package com.yas.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.yas.R;
import com.yas.api.ApiService;
import com.yas.api.PtApiService;
import com.yas.api.PtRetrofitClient;
import com.yas.api.RetrofitClient;
import com.yas.data.FavoritoLocal;
import com.yas.data.FavoritoStorage;
import com.yas.model.PalavraResponse;
import com.yas.model.PtWordResponse;
import com.yas.util.LanguageManager;
import com.yas.util.WordOfTheDay;
import com.yas.util.YasConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private TextView tvPalavra, tvFonetica, tvDefinicao, tvExemplo, tvLoading, tvData, tvDiaSemana, btnFavorito, tvIdioma;
    private CardView cardPalavra;
    private PalavraResponse palavraAtual;
    private FavoritoStorage favoritoStorage;
    private LanguageManager languageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvPalavra = findViewById(R.id.tvPalavra);
        tvFonetica = findViewById(R.id.tvFonetica);
        tvDefinicao = findViewById(R.id.tvDefinicao);
        tvExemplo = findViewById(R.id.tvExemplo);
        tvLoading = findViewById(R.id.tvLoading);
        tvData = findViewById(R.id.tvData);
        tvDiaSemana = findViewById(R.id.tvDiaSemana);
        btnFavorito = findViewById(R.id.btnFavorito);
        tvIdioma = findViewById(R.id.tvIdioma);
        cardPalavra = findViewById(R.id.cardPalavra);
        favoritoStorage = new FavoritoStorage(this);
        languageManager = new LanguageManager(this);

        mostrarDataAtual();
        atualizarBotaoIdioma();
        carregarPalavraDoDia();
    }

    private void mostrarDataAtual() {
        SimpleDateFormat dataFmt = new SimpleDateFormat("MMM dd", Locale.US);
        SimpleDateFormat diaSemanaFmt = new SimpleDateFormat("EEEE", Locale.US);
        Date hoje = new Date();

        tvData.setText(dataFmt.format(hoje));
        String dia = diaSemanaFmt.format(hoje);
        tvDiaSemana.setText(dia.substring(0, 1).toUpperCase() + dia.substring(1));
    }

    /** Alterna entre EN e PT. */
    public void onIdiomaClick(View view) {
        languageManager.toggle();
        atualizarBotaoIdioma();
        carregarPalavraDoDia();
    }

    private void atualizarBotaoIdioma() {
        tvIdioma.setText(languageManager.isEN() ? "EN" : "PT");
    }

    private void carregarPalavraDoDia() {
        tvLoading.setVisibility(View.VISIBLE);
        cardPalavra.setVisibility(View.GONE);

        String palavra = WordOfTheDay.get(languageManager.getIdioma());
        tvPalavra.setText(palavra);

        if (languageManager.isEN()) {
            buscarEN(palavra);
        } else {
            buscarPT(palavra);
        }
    }

    // ── Busca em inglês (Free Dictionary API) ──

    private void buscarEN(String palavra) {
        ApiService api = RetrofitClient.getService();
        api.buscar(palavra).enqueue(new Callback<List<PalavraResponse>>() {
            @Override
            public void onResponse(Call<List<PalavraResponse>> call, Response<List<PalavraResponse>> response) {
                tvLoading.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    palavraAtual = null; // não usar PalavraResponse para PT
                    PalavraResponse en = response.body().get(0);
                    exibirPalavraEN(en);
                } else {
                    Toast.makeText(HomeActivity.this, "Palavra não encontrada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PalavraResponse>> call, Throwable t) {
                tvLoading.setVisibility(View.GONE);
                Toast.makeText(HomeActivity.this, "Sem conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void exibirPalavraEN(PalavraResponse palavra) {
        tvPalavra.setText(palavra.word);

        if (palavra.phonetic != null && !palavra.phonetic.isEmpty()) {
            tvFonetica.setText(palavra.phonetic);
        } else if (palavra.phonetics != null && !palavra.phonetics.isEmpty()
                && palavra.phonetics.get(0).text != null) {
            tvFonetica.setText(palavra.phonetics.get(0).text);
        } else {
            tvFonetica.setText("");
        }

        if (palavra.meanings != null && !palavra.meanings.isEmpty()) {
            PalavraResponse.Definition def = palavra.meanings.get(0).definitions.get(0);
            tvDefinicao.setText(def.definition);

            if (def.example != null && !def.example.isEmpty()) {
                tvExemplo.setText("\u201C" + def.example + "\u201D");
                tvExemplo.setVisibility(View.VISIBLE);
            } else {
                tvExemplo.setVisibility(View.GONE);
            }
        }

        cardPalavra.setVisibility(View.VISIBLE);
        tvLoading.setVisibility(View.GONE);

        if (favoritoStorage.isSalvo(palavra.word)) {
            btnFavorito.setText("♥  Saved");
        } else {
            btnFavorito.setText("♡  Save");
        }
    }

    // ── Busca em português (Dicionário Aberto) ──

    private void buscarPT(String palavra) {
        PtApiService api = PtRetrofitClient.getService();
        api.buscar(palavra).enqueue(new Callback<List<PtWordResponse>>() {
            @Override
            public void onResponse(Call<List<PtWordResponse>> call, Response<List<PtWordResponse>> response) {
                tvLoading.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    PtWordResponse pt = response.body().get(0);
                    exibirPalavraPT(pt, palavra);
                } else {
                    Toast.makeText(HomeActivity.this, "Palavra não encontrada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PtWordResponse>> call, Throwable t) {
                tvLoading.setVisibility(View.GONE);
                Toast.makeText(HomeActivity.this, "Sem conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void exibirPalavraPT(PtWordResponse pt, String palavra) {
        palavraAtual = null; // PT não usa PalavraResponse
        tvPalavra.setText(palavra);
        tvFonetica.setText(""); // Dicionário Aberto não tem fonética

        // Extrair definições do XML
        String definicoes = extrairDefinicoesPT(pt.xml);
        tvDefinicao.setText(definicoes);
        tvExemplo.setVisibility(View.GONE);

        cardPalavra.setVisibility(View.VISIBLE);
        tvLoading.setVisibility(View.GONE);

        if (favoritoStorage.isSalvo(palavra)) {
            btnFavorito.setText("♥  Saved");
        } else {
            btnFavorito.setText("♡  Save");
        }
    }

    /** Extrai o texto das tags <def> do XML do Dicionário Aberto. */
    private String extrairDefinicoesPT(String xml) {
        if (xml == null || xml.isEmpty()) return "Definição não encontrada.";

        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("<def>(.*?)</def>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(xml);

        int count = 0;
        while (matcher.find() && count < 3) {
            String def = matcher.group(1).trim();
            // Remove tags internas e limpa
            def = def.replaceAll("<[^>]+>", "").trim();
            // Remove numeração de exemplo do tipo _texto_
            def = def.replaceAll("_", "").trim();
            if (def.isEmpty()) continue;
            if (count > 0) sb.append("\n\n");
            sb.append((count + 1) + ". " + def);
            count++;
        }

        return sb.length() > 0 ? sb.toString() : "Definição não encontrada.";
    }

    // ── Action Buttons ──

    public void onListenClick(View view) {
        String palavra = tvPalavra.getText().toString();
        if (palavra.isEmpty()) return;

        String lang = languageManager.isEN() ? "en" : "pt";
        String audioUrl = YasConfig.getTtsUrl(palavra, lang);

        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> mp.start());
            mediaPlayer.setOnCompletionListener(mp -> mp.release());
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                Toast.makeText(this, "Erro ao tocar áudio", Toast.LENGTH_SHORT).show();
                mp.release();
                return true;
            });
            Toast.makeText(this, "🔊 " + palavra, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onFavoriteClick(View view) {
        String palavra = tvPalavra.getText().toString();
        if (palavra.isEmpty()) return;

        String definicao = tvDefinicao.getText().toString();
        String fonetica = tvFonetica.getText().toString();

        if (favoritoStorage.isSalvo(palavra)) {
            favoritoStorage.remover(palavra);
            btnFavorito.setText("♡  Save");
            Toast.makeText(this, "Removido dos favoritos", Toast.LENGTH_SHORT).show();
        } else {
            FavoritoLocal fav = new FavoritoLocal(palavra, definicao, fonetica);
            if (favoritoStorage.salvar(fav)) {
                btnFavorito.setText("♥  Saved");
                Toast.makeText(this, "Palavra salva!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Palavra já salva", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onShareClick(View view) {
        String palavra = tvPalavra.getText().toString();
        if (palavra.isEmpty()) return;

        String texto = "📖 " + palavra + " — " + tvDefinicao.getText().toString();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, texto + "\n\nby YAS — Your Amazing Sentences");
        startActivity(Intent.createChooser(share, "Compartilhar"));
    }

    // ── Bottom Nav ──

    public void onNavHomeClick(View view) {
        carregarPalavraDoDia();
    }

    public void onNavSearchClick(View view) {
        startActivity(new Intent(this, SearchActivity.class));
    }

    public void onNavSavedClick(View view) {
        startActivity(new Intent(this, SavedWordsActivity.class));
    }
}