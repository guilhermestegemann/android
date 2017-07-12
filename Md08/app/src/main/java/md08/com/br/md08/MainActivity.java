package md08.com.br.md08;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    Spinner listaCursos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter add = ArrayAdapter.createFromResource(this,  R.array.cursos ,android.R.layout.simple_spinner_item);
        listaCursos = (Spinner) findViewById(R.id.CursosSpinner);
        listaCursos.setAdapter(add);
        Button ok = (Button) findViewById(R.id.OkButton);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView status = (TextView) findViewById(R.id.txtStatus);
        int op = listaCursos.getSelectedItemPosition();
        status.setText("Status: Curso "+ listaCursos.getAdapter().getItem(op).toString());
    }
}
