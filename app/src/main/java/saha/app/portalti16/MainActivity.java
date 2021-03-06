package saha.app.portalti16;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saha.app.portalti16.adapter.MahasiswaAdapter;
import saha.app.portalti16.entity.DaftarMahasiswa;
import saha.app.portalti16.network.Network;
import saha.app.portalti16.network.Routes;


public class MainActivity extends AppCompatActivity{

    RecyclerView lstMahasiswa;
    Button btnMahasiswa;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //casting recycler
        lstMahasiswa = (RecyclerView)findViewById(R.id.lst_mahasiswa);
        lstMahasiswa.setLayoutManager(new LinearLayoutManager(this));

        btnMahasiswa = (Button) findViewById(R.id.btn_to_add);

        requestDaftarMahasiswa();
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestDaftarMahasiswa();
        onButtonMahasiswa();
    }

    private void requestDaftarMahasiswa(){
        //pertama, memanggil request dari retrofit yang sudah dibuat
        Routes services = Network.request().create(Routes.class);

        //kite melakukan request terhadap getMahasiswa()
        services.getMahasiswa().enqueue(new Callback<DaftarMahasiswa>() {
            @Override
            public void onResponse(Call<DaftarMahasiswa> call, Response<DaftarMahasiswa> response) {
                //cek request yang dilakukan, berhasil atau tidak
                if(response.isSuccessful()){
                    //casting data yang didapatkan menjadi DaftarMahasiswa
                    DaftarMahasiswa mahasiswas = response.body();

                    //get title
                    Log.d("TI16", mahasiswas.getTitle());

                    //tampilkan daftar mahasiswa di recycler view
                    MahasiswaAdapter adapter = new MahasiswaAdapter(mahasiswas.getData());
                    lstMahasiswa.setAdapter(adapter);
                }else{
                    onMahasiswaError();
                }

            }

            @Override
            public void onFailure(Call<DaftarMahasiswa> call, Throwable t) {

            }
        });
    }

    private void onMahasiswaError(){
        Toast.makeText(MainActivity.this,"Gagal, Silahkan periksa koneksi internet anda",Toast.LENGTH_LONG).show();
    }

    private void onButtonMahasiswa(){
        btnMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(MainActivity.this, AddMahasiswaActivity.class);
                startActivity(pindah);
            }
        });
    }


}
