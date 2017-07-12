package md08.com.br.md082;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by Gui on 04/03/2017.
 */

public class Tela3 extends Activity implements  OnClickListener {

    TextView txtNome;
    TextView txtSexo;
    TextView txtEstadoCivil;
    TextView txtTelefone;
    TextView txtTipoTelefone;
    TextView txtCursos;
    TextView txtHorario;
    TextView txtLocal;
    Button btnDiscar;
    Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_3);

        Intent intent = getIntent();
        Bundle parametros = intent.getExtras();
        contato =  (Contato)parametros.getSerializable("CONTATO");
        preencherCampos(contato);

        btnDiscar = (Button) findViewById(R.id.btnDiscar);
        btnDiscar.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:"+txtTelefone.getText().toString());
                Intent intent = new Intent(Intent.ACTION_DIAL,uri);

                startActivity(intent);
            }
        });


    }

    private void preencherCampos(Contato contato) {
        txtNome = (TextView) findViewById(R.id.txtNome);
        txtSexo = (TextView) findViewById(R.id.txtSexo);
        txtEstadoCivil = (TextView) findViewById(R.id.txtEstadoCivil);
        txtTelefone = (TextView) findViewById(R.id.txtTelefone);
        txtTipoTelefone = (TextView) findViewById(R.id.txtTipoTelefone);
        txtCursos = (TextView) findViewById(R.id.txtCursos);
        txtHorario = (TextView) findViewById(R.id.txtHorario);
        txtLocal = (TextView) findViewById(R.id.txtLocal);

        txtNome.setText(contato.getNome());
        txtSexo.setText(contato.getSexo());
        txtEstadoCivil.setText(contato.getEstadoCivil());
        txtTelefone.setText(contato.getTelefone());
        txtTipoTelefone.setText(contato.getTipoTelefone());
        txtCursos.setText(contato.getCurso());
        txtLocal.setText(contato.getLocal());
        String sep = "";
        String teste = "";
        for (int i = 0; i < contato.getHorario().size(); i++){
            teste = txtHorario.getText().toString();
            if (contato.getHorario().size() > 1){
                if (!txtHorario.getText().toString().isEmpty()){
                    if (i == (contato.getHorario().size()-1)){
                        sep = " e ";
                    }else {
                        sep = " , ";
                    }
                }
            }
            txtHorario.setText(txtHorario.getText() +sep+ contato.getHorario().get(i).getNome());
        }
        txtHorario.setText(txtHorario.getText() + ".");
    }




    @Override
    public void onClick(View view) {

    }
}
