package md08.com.br.md082;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gui on 04/03/2017.
 */

public class Tela2 extends Activity implements  OnClickListener {

    TextView txtNome;
    TextView txtSexo;
    TextView txtEstadoCivil;
    TextView txtTelefone;
    TextView txtTipoTelefone;
    CheckBox cbManha;
    CheckBox cbTarde;
    CheckBox cbNoite;
    Spinner listaCursos;
    Spinner listaLocais;
    Button btnSalvar;
    Contato contato;
    private AlertDialog alerta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_2);
        ArrayAdapter listaCursosAdapter = ArrayAdapter.createFromResource(this,  R.array.cursos ,android.R.layout.simple_spinner_item);
        listaCursos = (Spinner) findViewById(R.id.CursosSpinner);
        listaCursos.setAdapter(listaCursosAdapter);

        ArrayAdapter listaLocaisAdapter = ArrayAdapter.createFromResource(this,  R.array.locais ,android.R.layout.simple_spinner_item);
        listaLocais = (Spinner) findViewById(R.id.LocaisSpinner);
        listaLocais.setAdapter(listaLocaisAdapter);

        cbManha = (CheckBox) findViewById(R.id.cbManha);
        cbTarde = (CheckBox) findViewById(R.id.cbTarde);
        cbNoite = (CheckBox) findViewById(R.id.cbNoite);

        Intent intencao = getIntent();
        Bundle parametros = intencao.getExtras();
        contato =  (Contato)parametros.getSerializable("CONTATO");
        preencherCampos(contato);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                montarObjeto();
                validarContato();

            }
        });
    }

    private void salvar() {
        Intent intent  = new Intent(this, Tela3.class);
        intent.putExtra("CONTATO",contato);
        startActivity(intent);
    }

    private void validarContato() {
        if (contato.getHorario().size() != 0){
            salvar();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Validação de Contato");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Todos os campos devem ser preenchidos!");
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alerta=builder.create();
            alerta.show();
        }

    }


    private void montarObjeto() {

        List<Horario> listHorario = new ArrayList<Horario>();
        Horario horario;
        if (cbManha.isChecked()){
            horario = new Horario();
            horario.setNome("Manhã");
            listHorario.add(horario);
        }
        if (cbTarde.isChecked()){
            horario = new Horario();
            horario.setNome("Tarde");
            listHorario.add(horario);
        }
        if (cbNoite.isChecked()){
            horario = new Horario();
            horario.setNome("Noite");
            listHorario.add(horario);
        }

        contato.setHorario(listHorario);
        contato.setCurso(listaCursos.getAdapter().getItem(listaCursos.getSelectedItemPosition()).toString());
        contato.setLocal(listaLocais.getAdapter().getItem(listaLocais.getSelectedItemPosition()).toString());
    }

    private void preencherCampos(Contato contato) {
        txtNome = (TextView) findViewById(R.id.txtNome);
        txtSexo = (TextView) findViewById(R.id.txtSexo);
        txtEstadoCivil = (TextView) findViewById(R.id.txtEstadoCivil);
        txtTelefone = (TextView) findViewById(R.id.txtTelefone);
        txtTipoTelefone = (TextView) findViewById(R.id.txtTipoTelefone);

        txtNome.setText(contato.getNome());
        txtSexo.setText(contato.getSexo());
        txtEstadoCivil.setText(contato.getEstadoCivil());
        txtTelefone.setText(contato.getTelefone());
        txtTipoTelefone.setText(contato.getTipoTelefone());

    }


    @Override
    public void onClick(View view) {

    }
}
