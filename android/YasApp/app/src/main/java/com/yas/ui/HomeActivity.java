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
import com.yas.api.RetrofitClient;
import com.yas.data.FavoritoLocal;
import com.yas.data.FavoritoStorage;
import com.yas.model.PalavraResponse;
import com.yas.util.WordOfTheDay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private TextView tvPalavra, tvFonetica, tvDefinicao, tvExemplo, tvLoading, tvData, tvDiaSemana, btnFavorito;
    private CardView cardPalavra;
    private PalavraResponse palavraAtual;
    private FavoritoStorage favoritoStorage;

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
        cardPalavra = findViewById(R.id.cardPalavra);
        favoritoStorage = new FavoritoStorage(this);

        mostrarDataAtual();
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

    private void carregarPalavraDoDia() {
        tvLoading.setVisibility(View.VISIBLE);
        cardPalavra.setVisibility(View.GONE);

        String palavra = WordOfTheDay.getPalavraDoDia();
        tvPalavra.setText(palavra);

        ApiService api = RetrofitClient.getService();
        api.buscar(palavra).enqueue(new Callback<List<PalavraResponse>>() {
            @Override
            public void onResponse(Call<List<PalavraResponse>> call, Response<List<PalavraResponse>> response) {
                tvLoading.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    palavraAtual = response.body().get(0);
                    exibirPalavra(palavraAtual);
                } else {
                    Toast.makeText(HomeActivity.this, "Erro ao carregar definição", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PalavraResponse>> call, Throwable t) {
                tvLoading.setVisibility(View.GONE);
                Toast.makeText(HomeActivity.this, "Sem conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void exibirPalavra(PalavraResponse palavra) {
        tvPalavra.setText(palavra.word);

        // Fonética
        if (palavra.phonetic != null && !palavra.phonetic.isEmpty()) {
            tvFonetica.setText(palavra.phonetic);
        } else if (palavra.phonetics != null && !palavra.phonetics.isEmpty()
                && palavra.phonetics.get(0).text != null) {
            tvFonetica.setText(palavra.phonetics.get(0).text);
        } else {
            tvFonetica.setText("");
        }

        // Definição e exemplo
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

        // Atualizar botão de favorito
        if (favoritoStorage.isSalvo(palavra.word)) {
            btnFavorito.setText("♥  Saved");
        } else {
            btnFavorito.setText("♡  Save");
        }
    }

    // ── Action Buttons ──

    public void onListenClick(View view) {
        if (palavraAtual == null) return;

        // Pega a URL de áudio do primeiro phonetic que tiver áudio
        String audioUrl = null;
        if (palavraAtual.phonetics != null) {
            for (PalavraResponse.PhoneticInfo p : palavraAtual.phonetics) {
                if (p.audio != null && !p.audio.isEmpty()) {
                    audioUrl = p.audio;
                    break;
                }
            }
        }

        if (audioUrl != null) {
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
                Toast.makeText(this, "🔊 " + palavraAtual.word, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Áudio não disponível para esta palavra", Toast.LENGTH_SHORT).show();
        }
    }

    public void onFavoriteClick(View view) {
        if (palavraAtual == null) return;

        String definicao = "";
        if (palavraAtual.meanings != null && !palavraAtual.meanings.isEmpty()) {
            definicao = palavraAtual.meanings.get(0).definitions.get(0).definition;
        }

        String fonetica = "";
        if (palavraAtual.phonetic != null) {
            fonetica = palavraAtual.phonetic;
        } else if (palavraAtual.phonetics != null && !palavraAtual.phonetics.isEmpty()
                && palavraAtual.phonetics.get(0).text != null) {
            fonetica = palavraAtual.phonetics.get(0).text;
        }

        // Se já está salvo, remove
        if (favoritoStorage.isSalvo(palavraAtual.word)) {
            favoritoStorage.remover(palavraAtual.word);
            btnFavorito.setText("♡  Save");
            Toast.makeText(this, "Removido dos favoritos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Salva localmente
        FavoritoLocal fav = new FavoritoLocal(palavraAtual.word, definicao, fonetica);
        if (favoritoStorage.salvar(fav)) {
            btnFavorito.setText("♥  Saved");
            Toast.makeText(this, "Palavra salva!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Palavra já salva", Toast.LENGTH_SHORT).show();
        }
    }

    public void onShareClick(View view) {
        if (palavraAtual == null) return;

        String texto = "📖 " + palavraAtual.word + " — " + tvDefinicao.getText().toString();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, texto + "\n\nby YAS — Your Amazing Sentences");
        startActivity(Intent.createChooser(share, "Compartilhar"));
    }

    // ── Bottom Nav ──

    public void onNavHomeClick(View view) {
        // Já está na Home, só recarrega
        carregarPalavraDoDia();
    }

    public void onNavSearchClick(View view) {
        startActivity(new Intent(this, SearchActivity.class));
    }

    public void onNavSavedClick(View view) {
        startActivity(new Intent(this, SavedWordsActivity.class));
    }
}