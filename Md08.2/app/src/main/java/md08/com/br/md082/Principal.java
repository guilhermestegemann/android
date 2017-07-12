package md08.com.br.md082;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

public class Principal extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Contato contato;
    private ListView lvEstadoCivil;
    private EditText edtNome;
    private RadioButton rbMasculino;
    private RadioButton rbFeminino;
    private EditText edtTelefone;
    private RadioButton rbFixo;
    private RadioButton rbCelular;
    private RadioButton rbTrabalho;
    private Button btnSalvar;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        contato = new Contato();

        edtNome = (EditText)findViewById(R.id.edtNome);
        rbMasculino = (RadioButton) findViewById(R.id.rbMasculino);
        rbFeminino = (RadioButton) findViewById(R.id.rbFeminino);
        ArrayAdapter adapterEstadoCivil = ArrayAdapter.createFromResource(this,  R.array.estado_civil ,android.R.layout.select_dialog_singlechoice);
        lvEstadoCivil = (ListView) findViewById(R.id.lvEstadoCivil);
        lvEstadoCivil.setAdapter(adapterEstadoCivil);
        lvEstadoCivil.setOnItemClickListener(this);
        edtTelefone = (EditText) findViewById(R.id.edtTelefone);
        rbFixo = (RadioButton) findViewById(R.id.rbFixo);
        rbCelular = (RadioButton) findViewById(R.id.rbCelular);
        rbTrabalho = (RadioButton) findViewById(R.id.rbTrabalho);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        salvar();
    }

    private void salvar() {
        contato.setNome(edtNome.getText().toString());
        if (rbMasculino.isChecked()){
            contato.setSexo("Masculino");
        }
        if (rbFeminino.isChecked()){
            contato.setSexo("Feminino");
        }
        contato.setTelefone(edtTelefone.getText().toString());
        if (rbFixo.isChecked()){
            contato.setTipoTelefone("Fixo");
        }
        if (rbCelular.isChecked()){
            contato.setTipoTelefone("Celular");
        }
        if (rbTrabalho.isChecked()){
            contato.setTipoTelefone("Trabalho");
        }
        if (!validaContato(contato)){
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
        }else{
            Intent intent  = new Intent(this, Tela2.class);
            intent.putExtra("CONTATO",contato);
            startActivity(intent);
        }
    }

    private boolean validaContato(Contato contato) {
        if (contato.getNome().isEmpty()||contato.getSexo() == null||contato.getEstadoCivil() == null||contato.getTelefone().isEmpty()||contato.getTipoTelefone() == null){
            return false;
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                contato.setEstadoCivil("Solteiro(a)");
                break;
            case 1:
                contato.setEstadoCivil("Casado(a)");
                break;
            case 2:
                contato.setEstadoCivil("Divorciado(a)");
                break;
            case 3:
                contato.setEstadoCivil("Viúvo(a)");
                break;
            case 4:
                contato.setEstadoCivil("Separado(a)");
                break;
        }
    }
}
