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
import com.yas.api.FavoritoRequest;
import com.yas.api.RetrofitClient;
import com.yas.model.PalavraResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private TextView tvPalavra, tvFonetica, tvDefinicao, tvExemplo, tvLoading, tvData, tvDiaSemana, btnFavorito;
    private CardView cardPalavra;
    private PalavraResponse palavraAtual;

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

        ApiService api = RetrofitClient.getService();
        api.palavraDoDia().enqueue(new Callback<PalavraResponse>() {
            @Override
            public void onResponse(Call<PalavraResponse> call, Response<PalavraResponse> response) {
                tvLoading.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    palavraAtual = response.body();
                    exibirPalavra(palavraAtual);
                } else {
                    Toast.makeText(HomeActivity.this, "Erro ao carregar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PalavraResponse> call, Throwable t) {
                tvLoading.setVisibility(View.GONE);
                Toast.makeText(HomeActivity.this, "Sem conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void exibirPalavra(PalavraResponse palavra) {
        tvPalavra.setText(palavra.palavra);
        tvFonetica.setText(palavra.fonetica != null ? palavra.fonetica : "");

        if (palavra.significados != null && !palavra.significados.isEmpty()) {
            PalavraResponse.Definicao def = palavra.significados.get(0).definicoes.get(0);
            tvDefinicao.setText(def.definicao);

            if (def.exemplo != null && !def.exemplo.isEmpty()) {
                tvExemplo.setText("\u201C" + def.exemplo + "\u201D");
                tvExemplo.setVisibility(View.VISIBLE);
            } else {
                tvExemplo.setVisibility(View.GONE);
            }
        }

        cardPalavra.setVisibility(View.VISIBLE);
        tvLoading.setVisibility(View.GONE);
    }

    // ── Action Buttons ──

    public void onListenClick(View view) {
        if (palavraAtual == null) return;
        Toast.makeText(this, "🔊 " + palavraAtual.palavra, Toast.LENGTH_SHORT).show();
    }

    public void onFavoriteClick(View view) {
        if (palavraAtual == null) return;

        String definicao = "";
        if (palavraAtual.significados != null && !palavraAtual.significados.isEmpty()) {
            definicao = palavraAtual.significados.get(0).definicoes.get(0).definicao;
        }

        String fonetica = palavraAtual.fonetica != null ? palavraAtual.fonetica : "";

        RetrofitClient.getService().salvarFavorito(
                new FavoritoRequest(palavraAtual.palavra, definicao, fonetica)
        ).enqueue(new Callback<com.yas.api.FavoritoResponse>() {
            @Override
            public void onResponse(Call<com.yas.api.FavoritoResponse> call, Response<com.yas.api.FavoritoResponse> response) {
                if (response.isSuccessful()) {
                    btnFavorito.setText("♥  Saved");
                    Toast.makeText(HomeActivity.this, "Palavra salva!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.yas.api.FavoritoResponse> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onShareClick(View view) {
        if (palavraAtual == null) return;

        String texto = "📖 " + palavraAtual.palavra + " — " + tvDefinicao.getText().toString();
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
        Toast.makeText(this, "Busca disponível em breve", Toast.LENGTH_SHORT).show();
    }

    public void onNavSavedClick(View view) {
        Toast.makeText(this, "Favoritos disponível em breve", Toast.LENGTH_SHORT).show();
    }
}