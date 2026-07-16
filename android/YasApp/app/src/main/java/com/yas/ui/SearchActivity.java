package com.yas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.yas.R;
import com.yas.api.ApiService;
import com.yas.api.RetrofitClient;
import com.yas.data.FavoritoLocal;
import com.yas.data.FavoritoStorage;
import com.yas.model.PalavraResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private EditText etBusca;
    private TextView tvLoading, tvPalavra, tvFonetica, tvDefinicao, tvExemplo, tvNotFound, btnFavorito;
    private CardView cardResultado;
    private PalavraResponse palavraAtual;
    private FavoritoStorage favoritoStorage;

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
        cardResultado = findViewById(R.id.cardResultado);
        favoritoStorage = new FavoritoStorage(this);

        // Buscar ao pressionar Enter no teclado
        etBusca.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                buscar();
                return true;
            }
            return false;
        });
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

        ApiService api = RetrofitClient.getService();
        api.buscar(q).enqueue(new Callback<List<PalavraResponse>>() {
            @Override
            public void onResponse(Call<List<PalavraResponse>> call, Response<List<PalavraResponse>> response) {
                tvLoading.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    palavraAtual = response.body().get(0);
                    exibirResultado(palavraAtual);
                } else {
                    tvNotFound.setText("Palavra não encontrada: \"" + q + "\"");
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

    private void exibirResultado(PalavraResponse palavra) {
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

        // Atualizar botão de favorito
        if (favoritoStorage.isSalvo(palavra.palavra)) {
            btnFavorito.setText("♥  Saved");
        } else {
            btnFavorito.setText("♡  Save");
        }

        cardResultado.setVisibility(View.VISIBLE);
    }

    public void onFavoriteClick(View view) {
        if (palavraAtual == null) return;

        String definicao = "";
        if (palavraAtual.significados != null && !palavraAtual.significados.isEmpty()) {
            definicao = palavraAtual.significados.get(0).definicoes.get(0).definicao;
        }

        if (favoritoStorage.isSalvo(palavraAtual.palavra)) {
            favoritoStorage.remover(palavraAtual.palavra);
            btnFavorito.setText("♡  Save");
        } else {
            FavoritoLocal fav = new FavoritoLocal(
                palavraAtual.palavra, definicao,
                palavraAtual.fonetica != null ? palavraAtual.fonetica : ""
            );
            favoritoStorage.salvar(fav);
            btnFavorito.setText("♥  Saved");
        }
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
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    public void onNavSavedClick(View view) {
        startActivity(new Intent(this, SavedWordsActivity.class));
    }
}