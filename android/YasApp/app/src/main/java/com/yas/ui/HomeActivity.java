package com.yas.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.yas.R;
import com.yas.api.ApiService;
import com.yas.api.RetrofitClient;
import com.yas.model.PalavraResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private TextView tvPalavra, tvFonetica, tvDefinicao, tvExemplo, tvLoading;
    private CardView cardPalavra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvPalavra = findViewById(R.id.tvPalavra);
        tvFonetica = findViewById(R.id.tvFonetica);
        tvDefinicao = findViewById(R.id.tvDefinicao);
        tvExemplo = findViewById(R.id.tvExemplo);
        tvLoading = findViewById(R.id.tvLoading);
        cardPalavra = findViewById(R.id.cardPalavra);

        carregarPalavraDoDia();
    }

    private void carregarPalavraDoDia() {
        tvLoading.setVisibility(TextView.VISIBLE);
        cardPalavra.setVisibility(TextView.GONE);

        ApiService api = RetrofitClient.getService();
        api.palavraDoDia().enqueue(new Callback<PalavraResponse>() {
            @Override
            public void onResponse(Call<PalavraResponse> call, Response<PalavraResponse> response) {
                tvLoading.setVisibility(TextView.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    exibirPalavra(response.body());
                } else {
                    Toast.makeText(HomeActivity.this, "Erro ao carregar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PalavraResponse> call, Throwable t) {
                tvLoading.setVisibility(TextView.GONE);
                Toast.makeText(HomeActivity.this, "Sem conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void exibirPalavra(PalavraResponse palavra) {
        tvPalavra.setText(palavra.palavra);
        tvFonetica.setText(palavra.fonetica);

        if (palavra.significados != null && !palavra.significados.isEmpty()) {
            PalavraResponse.Definicao def = palavra.significados.get(0).definicoes.get(0);
            tvDefinicao.setText(def.definicao);
            if (def.exemplo != null && !def.exemplo.isEmpty()) {
                tvExemplo.setText("\u201C" + def.exemplo + "\u201D");
                tvExemplo.setVisibility(TextView.VISIBLE);
            } else {
                tvExemplo.setVisibility(TextView.GONE);
            }
        }

        cardPalavra.setVisibility(TextView.VISIBLE);
    }
}
