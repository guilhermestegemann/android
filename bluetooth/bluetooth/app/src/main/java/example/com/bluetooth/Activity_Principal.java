package example.com.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class Activity_Principal extends Activity implements OnClickListener{
    private static final int REQUEST_ENABLE_ATIVAR = 1;
    private static final int REQUEST_ENABLE_EXIBIR = 2;
    private static final int REQUEST_TEMPO = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__principal);
        Button btnAtivatBluetooth = (Button)findViewById(R.id.btnAtivarBluetooth);
        Button btnListar = (Button)findViewById(R.id.btnListar);

        btnAtivatBluetooth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                BluetoothAdapter mBluetoothAdapter =  BluetoothAdapter.getDefaultAdapter();
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_ATIVAR);
                }else{
                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, REQUEST_TEMPO);
                    startActivityForResult(discoverableIntent, REQUEST_ENABLE_EXIBIR);
                }

            }
        });

        btnListar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ListView listView = (ListView)findViewById(R.id.lvListaDispositivos);
                ArrayList<String> arrayList = new ArrayList<>();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item ,arrayList);
                listView.setAdapter(adapter);
                BluetoothAdapter mBluetoothAdapter =  BluetoothAdapter.getDefaultAdapter();
                Set<BluetoothDevice> dispPareados = mBluetoothAdapter.getBondedDevices();
                if (dispPareados.size()>0){
                    for (BluetoothDevice disp : dispPareados){
                        arrayList.add("Nome: " + disp.getName()+ " Endereço: " + disp.getAddress());

                    }
                    //adapter.notifyDataSetChanged();
                }
            }
        });
    }





    protected  void onActivityResult(int requestCode, int resultCode, Intent data){

        Button btn = (Button)findViewById(R.id.btnAtivarBluetooth);
        TextView lblStatus = (TextView)findViewById(R.id.lblStatus);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_ATIVAR){
            if (resultCode == RESULT_OK){
                lblStatus.setText("Ativado");
                btn.setText("Exibir");
            }else{
                lblStatus.setText("Não autorizado");
            }
        }
        if (requestCode == REQUEST_ENABLE_EXIBIR) {
            if (resultCode == REQUEST_TEMPO) {
                lblStatus.setText("Exibindo por "+String.valueOf(REQUEST_TEMPO)+ " seg.");
            } else {
                lblStatus.setText("Não autorizado exibir");
            }
        }
    }

    @Override
    public void onClick(View view) {

    }
}
