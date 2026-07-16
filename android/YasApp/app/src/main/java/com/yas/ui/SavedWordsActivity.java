package com.yas.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yas.R;
import com.yas.data.FavoritoLocal;
import com.yas.data.FavoritoStorage;

import java.util.List;

public class SavedWordsActivity extends AppCompatActivity {

    private RecyclerView listaFavoritos;
    private TextView tvVazio;
    private FavoritoStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_words);

        listaFavoritos = findViewById(R.id.listaFavoritos);
        tvVazio = findViewById(R.id.tvVazio);
        storage = new FavoritoStorage(this);

        carregarFavoritos();
    }

    private void carregarFavoritos() {
        List<FavoritoLocal> favoritos = storage.listar();

        if (favoritos.isEmpty()) {
            tvVazio.setVisibility(View.VISIBLE);
            listaFavoritos.setVisibility(View.GONE);
        } else {
            tvVazio.setVisibility(View.GONE);
            listaFavoritos.setVisibility(View.VISIBLE);
            listaFavoritos.setLayoutManager(new LinearLayoutManager(this));
            // Adapter simples inline
            listaFavoritos.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
                    android.widget.TextView tv = new android.widget.TextView(parent.getContext());
                    tv.setPadding(16, 20, 16, 20);
                    tv.setTextSize(18);
                    tv.setTextColor(0xFF2D2B2B);
                    tv.setBackgroundColor(0xFFFFFFFF);
                    return new RecyclerView.ViewHolder(tv) {};
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                    FavoritoLocal f = favoritos.get(position);
                    android.widget.TextView tv = (android.widget.TextView) holder.itemView;
                    tv.setText(f.palavra + " — " + f.definicao);
                }

                @Override
                public int getItemCount() {
                    return favoritos.size();
                }
            });
        }
    }

    public void onBackClick(View view) {
        finish();
    }
}