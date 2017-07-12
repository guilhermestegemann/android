package client.com.br.appclientb;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends Activity {

    //Objetos
    TextView txvServerSelecionado;
    Button btnCnServer;
    EditText edtMsg;
    Button btnEnviarMsg;
    TextView txvMensagem;
    BluetoothDevice deviceServer;
    OutputStream outpEnviar;
    InputStream inpRec;
    BluetoothSocket socket;
    BluetoothAdapter objBAdapter;

    private static final UUID UUID_ = UUID.fromString("6eee98c0-2ba8-11e7-9598-0800200c9a66");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvServerSelecionado = (TextView) findViewById(R.id.txvServerSelecionado);
        btnCnServer = (Button) findViewById(R.id.btnConectarServer);
        edtMsg = (EditText) findViewById(R.id.edtMsg);
        btnEnviarMsg = (Button) findViewById(R.id.btnEnviarMsg);
        txvMensagem = (TextView) findViewById(R.id.txvRetornoServer);
        //instancia o objeto bluetooth
        objBAdapter = BluetoothAdapter.getDefaultAdapter();
        //metodo que verifica se o bluetooth está ativo e ativa ele se necessário
        verificarAtivarBluetooth();
        //atribui um BluetoothDevice ao obj deviceServer passando o Address do servidor
        deviceServer = objBAdapter.getRemoteDevice("9C:65:B0:F4:0D:EE");
        //atribui dados do dispositivo servidor ao label
        txvServerSelecionado.setText(deviceServer.getName().toString() + " - "
            + deviceServer.getAddress());
        //metodo click do botão conectar
        btnCnServer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //chamando metodo conectar
                conectarServer();
            }
        });
        //metodo click do botao enviar
        btnEnviarMsg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //chamando metodo enviar msg
                enviarMsg();
            }
        });
    }

    protected void enviarMsg() {
        //verificar se o output é nulo
        if (outpEnviar != null){
            try {
                //escreve no output o que está no edit, em bytes
                outpEnviar.write(edtMsg.getText().toString().getBytes());
                //cria um array de byte com tamanho de 1024
                byte[] msgArray = new byte[1024];
                //pega o tamanho do que foi lido no inpRec (retorno do servidor)
                int tamanho = inpRec.read(msgArray);
                //instancia uma string passando o array de bytes, o offset, e o tamanho
                String msg = new String(msgArray,0,tamanho);
                //atribui a mensagem ao label específico
                txvMensagem.setText(txvMensagem.getText() + msg + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void conectarServer() {
        try {
            //Criar um RFCOMM BluetoothSocketpronto para iniciar uma conexão de saída segura para este dispositivo remoto usando pesquisa SDP de uuid.
            //Isto é projetado para ser usado com listenUsingRfcommWithServiceRecord(String, UUID)para aplicações Bluetooth peer-pares
            socket = deviceServer.createRfcommSocketToServiceRecord(UUID_);
            //Use connect() para iniciar a conexão de saída
            socket.connect();
            //criando output
            outpEnviar = socket.getOutputStream();
            //criando input
            inpRec = socket.getInputStream();
            if (outpEnviar != null){
                //atribui o texto CONECTADO concatenado ao label do server selecionado
                txvServerSelecionado.setText(txvServerSelecionado.getText().toString()+ " - CONECTADO");

            }
        }catch (Exception e){
            Toast.makeText(this,"Erro: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    

    private void verificarAtivarBluetooth() {
        try{
            if (objBAdapter.isEnabled()){
                Toast.makeText(this,"Bluetooth ativo!",Toast.LENGTH_SHORT).show();
            }else{
                Intent intentB = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intentB,0);
            }
        }catch(Exception e){
            Toast.makeText(this,"Erro: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
