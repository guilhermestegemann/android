package bluetoothapp.com.br.bluetoothapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    //Definir as variaves e os objetos
    Button btnVerificarAtivar;
    Button bntListarDispositivosPareados;
    Button btnBuscarNovosDispositivos;
    TextView txvMensagens;
    ListView listViewDiversos;
    BluetoothAdapter objBtAdapter;
    List<BluetoothDevice> listaBtDevice;
    ProgressDialog objPgDialog;
    int qtdeDispositivosEncontrados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instancia o bluetooth adapter
        objBtAdapter = BluetoothAdapter.getDefaultAdapter();

        //atribui os componentes aos objetos
        btnVerificarAtivar = (Button) findViewById(R.id.btnVerificarAtivar);
        bntListarDispositivosPareados = (Button) findViewById(R.id.btnListarDispositivosPareados);
        btnBuscarNovosDispositivos = (Button) findViewById(R.id.btnBuscarNovosDispositivos);
        txvMensagens = (TextView) findViewById(R.id.txtMensagens);
        listViewDiversos = (ListView) findViewById(R.id.lvDiversos);
        qtdeDispositivosEncontrados = 0;

        //metdodo onclick do btnVerificarAtivar
        btnVerificarAtivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarAtivarBluetooth();
            }
        });

        //metodo listar dispositivos pareados
        bntListarDispositivosPareados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarDispositivosPareados();
            }
        });

        // metodo buscar novos dispositivos
        btnBuscarNovosDispositivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarDispositivosComBluetooth();
            }
        });
    }

    protected void verificarAtivarBluetooth() {
        if (objBtAdapter != null){//verifica se o dispositivo tem bluetooh
            Toast.makeText(this, "Dispositivo com bluetooth.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Dispositivo sem bluetooth.", Toast.LENGTH_SHORT).show();
        }

        if (objBtAdapter.isEnabled()){//metodo que retorna se o bluetooh está ativo
            Toast.makeText(this, "Bluetooth ativado.", Toast.LENGTH_SHORT).show();
        }else{// se nao tiver ativo, manda ativar
            //instancia uma intent (pedido feito ao SO) passando como parametro o metodo action_req_enable da classe bluetooth
            //esse metodo ativa o bluetooth
            Intent intentAtivarBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //Inicia a intent passando o request code
            startActivityForResult(intentAtivarBluetooth, 0);
        }
    }

    protected void listarDispositivosPareados() {
        //Garantir que o bluetooth está ativo
        verificarAtivarBluetooth();
        //instanvia a lista e como parametro pega os devices pelo objeto objBtAdapter
        listaBtDevice = new ArrayList<BluetoothDevice>(objBtAdapter.getBondedDevices());
        //método genérico pra preecher os dispositivos (parametro 1: pareados, 2: encontrados)
        preencherListViewDiversos(1);

    }

    private void preencherListViewDiversos(int i) {
        //instancia listDevice
        List<String> listDevice = new ArrayList<String>();
        //verifica o parametro passado
        if (i == 1){
            //laço usado para percorrer listaBtDevice
            for (BluetoothDevice dispositivo: listaBtDevice) {
                //adiciona o dispositivo na lista listDevice
                listDevice.add(dispositivo.getName() + " - " + dispositivo.getAddress() +
                    " --Pareados--");
            }
            //atribui no txv um texto
            txvMensagens.setText("Dispositivos pareados");
        }
        if (i == 2){
            //laço usado para percorrer listaBtDevice
            for (BluetoothDevice dispositivo: listaBtDevice) {
                listDevice.add(dispositivo.getName() + " - " + dispositivo.getAddress() +
                        " --Disp. Encontrados--");
            }
            txvMensagens.setText("Dispositivos encontrados: " + qtdeDispositivosEncontrados);
        }

        //arrayadapter usado pra preencher listView
        ArrayAdapter<String> adapterListView = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listDevice);
        //setando a lista através do adaptador
        listViewDiversos.setAdapter(adapterListView);
    }

    protected void buscarDispositivosComBluetooth() {
        //verifica o bluetooth se está ativo
        verificarAtivarBluetooth();
        //se tem busca em andamento, cancela a busca.
        if (objBtAdapter.isDiscovering()){
            objBtAdapter.cancelDiscovery();
        }
        //registra os dois broadcast receiver
        this.registerReceiver(meuBroadcastReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        this.registerReceiver(meuBroadcastReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
        //inicia a busca
        objBtAdapter.startDiscovery();
        //inicia o progress dialog
        objPgDialog = ProgressDialog.show(this, "Busca Bluetooth","Buscando novos dispositivos.");

    }
    //declara o broadcast como static
    private final BroadcastReceiver meuBroadcastReceiver = new BroadcastReceiver() {
        //sobreescreve o método onReceive do broad cast
        @Override
        public void onReceive(Context context, Intent intent) {
            //atribui a intenção numa string, pra comparar nas linhas abaixo
            String actionIntent = intent.getAction();
            //verifica se tem a instância da listaBtDevice
            if (listaBtDevice == null){
                listaBtDevice = new ArrayList<BluetoothDevice>();
            }
            //se a intenção for a de busca e econtrou algo
            if (BluetoothDevice.ACTION_FOUND.equals(actionIntent)) {
                //cria um obj disp da classe BtDevice
                //recupera ele pela intent
                BluetoothDevice dispositivo = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //adiciona na lista
                listaBtDevice.add(dispositivo);
                //incrementa a variavel qtdeDispositivos
                qtdeDispositivosEncontrados++;
                Toast.makeText(context, "Encontrado:" + dispositivo.getName() +
                        " - " + dispositivo.getAddress(), Toast.LENGTH_LONG).show();
            //se a intencao for o start de uma descoberta
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(actionIntent)) {
                Toast.makeText(context, "Foi iniciada uma busca por dispositivos bluetooth.",
                        Toast.LENGTH_LONG).show();
            //se a intencao for a finalizacao da descoberta
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(actionIntent)) {
                Toast.makeText(context, "Busca concluída. Encontrados: " + qtdeDispositivosEncontrados +
                        " dispositivos", Toast.LENGTH_LONG).show();
                objPgDialog.dismiss();
                preencherListViewDiversos(2);
            }

        }
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (objBtAdapter == null){
            objBtAdapter.cancelDiscovery();
        }

        this.unregisterReceiver(meuBroadcastReceiver);

    }
}
