package com.yas.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
import com.yas.util.YasConfig;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private EditText etBusca;
    private TextView tvLoading, tvPalavra, tvFonetica, tvDefinicao, tvExemplo, tvNotFound, btnFavorito, tvIdioma;
    private CardView cardResultado;
    private PalavraResponse palavraAtual;
    private FavoritoStorage favoritoStorage;
    private LanguageManager languageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etBusca = findViewById(R.id.etBusca);
        tvLoading = findViewById(R.id.tvLoading);
        tvPalavra = findViewById(R.id.tvPalavra);
        tvFonetica = findViewById(R.id.tvFonetica);
        tvDefinicao = findViewById(R.id.tvDefinicao);
        tvExemplo = findViewById(R.id.tvExemplo);
        tvNotFound = findViewById(R.id.tvNotFound);
        btnFavorito = findViewById(R.id.btnFavorito);
        tvIdioma = findViewById(R.id.tvIdioma);
        cardResultado = findViewById(R.id.cardResultado);
        favoritoStorage = new FavoritoStorage(this);
        languageManager = new LanguageManager(this);

        atualizarBotaoIdioma();
        atualizarHint();

        etBusca.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                buscar();
                return true;
            }
            return false;
        });
    }

    /** Alterna entre EN e PT. */
    public void onIdiomaClick(View view) {
        languageManager.toggle();
        atualizarBotaoIdioma();
        atualizarHint();
        // Limpa resultado anterior
        cardResultado.setVisibility(View.GONE);
        tvNotFound.setVisibility(View.GONE);
        palavraAtual = null;
    }

    private void atualizarBotaoIdioma() {
        tvIdioma.setText(languageManager.isEN() ? "EN" : "PT");
    }

    private void atualizarHint() {
        etBusca.setHint(languageManager.isEN() ? "Search any word..." : "Buscar palavra...");
    }

    public void onSearchClick(View view) {
        buscar();
    }

    private void buscar() {
        String q = etBusca.getText().toString().trim();
        if (q.isEmpty()) return;

        tvLoading.setVisibility(View.VISIBLE);
        cardResultado.setVisibility(View.GONE);
        tvNotFound.setVisibility(View.GONE);

        if (languageManager.isEN()) {
            buscarEN(q);
        } else {
            buscarPT(q);
        }
    }

    // ── Busca em inglês ──

    private void buscarEN(String palavra) {
        ApiService api = RetrofitClient.getService();
        api.buscar(palavra).enqueue(new Callback<List<PalavraResponse>>() {
            @Override
            public void onResponse(Call<List<PalavraResponse>> call, Response<List<PalavraResponse>> response) {
                tvLoading.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    palavraAtual = response.body().get(0);
                    exibirResultadoEN(palavraAtual);
                } else {
                    tvNotFound.setText("Palavra não encontrada: \"" + palavra + "\"");
                    tvNotFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<PalavraResponse>> call, Throwable t) {
                tvLoading.setVisibility(View.GONE);
                tvNotFound.setText("Erro de conexão: " + t.getMessage());
                tvNotFound.setVisibility(View.VISIBLE);
            }
        });
    }

    private void exibirResultadoEN(PalavraResponse palavra) {
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

        tvFonetica.setVisibility(View.VISIBLE);

        if (favoritoStorage.isSalvo(palavra.word)) {
            btnFavorito.setText("♥  Saved");
        } else {
            btnFavorito.setText("♡  Save");
        }

        cardResultado.setVisibility(View.VISIBLE);
    }

    // ── Busca em português ──

    private void buscarPT(String palavra) {
        PtApiService api = PtRetrofitClient.getService();
        api.buscar(palavra.toLowerCase()).enqueue(new Callback<List<PtWordResponse>>() {
            @Override
            public void onResponse(Call<List<PtWordResponse>> call, Response<List<PtWordResponse>> response) {
                tvLoading.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    palavraAtual = null;
                    PtWordResponse pt = response.body().get(0);
                    exibirResultadoPT(pt, palavra);
                } else {
                    mostrarErro("Palavra não encontrada: \"" + palavra + "\"");
                }
            }

            @Override
            public void onFailure(Call<List<PtWordResponse>> call, Throwable t) {
                tvLoading.setVisibility(View.GONE);
                mostrarErro("Erro de conexão: " + t.getMessage());
            }
        });
    }

    /** Mostra mensagem de erro de forma visível (não só Toast). */
    private void mostrarErro(String msg) {
        tvNotFound.setText(msg);
        tvNotFound.setVisibility(View.VISIBLE);
        cardResultado.setVisibility(View.GONE);
    }

    private void exibirResultadoPT(PtWordResponse pt, String palavra) {
        tvPalavra.setText(palavra);
        tvFonetica.setText("");
        tvFonetica.setVisibility(View.GONE);
        tvExemplo.setVisibility(View.GONE);

        String definicoes = extrairDefinicoesPT(pt.xml);
        tvDefinicao.setText(definicoes);

        if (favoritoStorage.isSalvo(palavra)) {
            btnFavorito.setText("♥  Saved");
        } else {
            btnFavorito.setText("♡  Save");
        }

        cardResultado.setVisibility(View.VISIBLE);
    }

    private String extrairDefinicoesPT(String xml) {
        if (xml == null || xml.isEmpty()) return "Definição não encontrada.";

        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("<def>(.*?)</def>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(xml);

        int count = 0;
        while (matcher.find() && count < 3) {
            String def = matcher.group(1).trim();
            def = def.replaceAll("<[^>]+>", "").trim();
            def = def.replaceAll("_", "").trim();
            if (def.isEmpty()) continue;
            if (count > 0) sb.append("\n\n");
            sb.append((count + 1) + ". " + def);
            count++;
        }

        return sb.length() > 0 ? sb.toString() : "Definição não encontrada.";
    }

    // ── Action Buttons ──

    public void onFavoriteClick(View view) {
        String palavra = tvPalavra.getText().toString();
        if (palavra.isEmpty()) return;

        String definicao = tvDefinicao.getText().toString();
        String fonetica = tvFonetica.getText().toString();

        if (favoritoStorage.isSalvo(palavra)) {
            favoritoStorage.remover(palavra);
            btnFavorito.setText("♡  Save");
        } else {
            FavoritoLocal fav = new FavoritoLocal(palavra, definicao, fonetica);
            favoritoStorage.salvar(fav);
            btnFavorito.setText("♥  Saved");
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

    // ── Bottom Nav ──

    public void onNavHomeClick(View view) {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    public void onNavSavedClick(View view) {
        startActivity(new Intent(this, SavedWordsActivity.class));
    }
}