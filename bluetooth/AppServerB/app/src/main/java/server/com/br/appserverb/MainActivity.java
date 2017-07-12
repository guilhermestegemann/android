package server.com.br.appserverb;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends Activity {

    //Objetos
    TextView txvEndServer;
    Button btnAtivarServer;
    TextView txvMsg;
    TextView txvNomeClienteCn;
    boolean cnServerAtivo = false;
    String nomeClienteCn, address, msgRec;
    BluetoothAdapter objBAdapter;
    //nome do servico usado pra criar o servidor com o socket
    private static final String NOME_SERVICO = "ServerBluetooth";
    //uuid usado pra criar o servidor com o socket
    //uuid é o identificador unico universal
    private static final UUID UUID_ = UUID.fromString("6eee98c0-2ba8-11e7-9598-0800200c9a66");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //atribui valores aos objetos
        txvEndServer = (TextView) findViewById(R.id.txvEndServer);
        btnAtivarServer = (Button) findViewById(R.id.btnAtivarServidor);
        txvMsg = (TextView) findViewById(R.id.txvMsg);
        txvNomeClienteCn = (TextView) findViewById(R.id.txvClienteCn);
        //método do botao ativar server
        btnAtivarServer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ativarServer();
            }
        });
    }

    private void ativarServer() {
        //instancia o objeto bluetooth
        objBAdapter = BluetoothAdapter.getDefaultAdapter();
        //chama o metodo pra verificar o bluetooth e ativar se tiver desativado
        verificarAtivarBluetooth();
        //atribui no txv o adress do obj bluetooth. esse adress sera usado no cliente pra conectar os dois dispositivos.
        txvEndServer.setText("Endereço server: "+objBAdapter.getAddress());
        //instancia a classe threadserver
        new ThreadServer().start();
    }
    //metodo que verifica e ativa o bluetooth
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
    //classe thread server
    class ThreadServer extends Thread {
        //Objetos
        private BluetoothSocket socket;// estabelece a conexao
        private BluetoothServerSocket btServerSocket;// servidor que expõe o serviço


        //sobrescrevendo o metodo run
        @Override
        public void run(){
            try {
                //expondo o socket pra que alguem possa conectar
                //Criar uma escuta, tomada RFCOMM Bluetooth insegura com Service Record.
                btServerSocket = objBAdapter.listenUsingInsecureRfcommWithServiceRecord(NOME_SERVICO, UUID_);
                //o accept serve para recuperar conexões de entrada de uma escuta
                socket = btServerSocket.accept();
                //se o socket nao for null
                if (socket != null){
                    //fecha o server pois já tem alguem conectado
                    btServerSocket.close();
                    //Input stream pra receber dados
                    InputStream inputRec = socket.getInputStream();
                    //Output pra enviar dados
                    OutputStream outputEnvio = socket.getOutputStream();
                    //instancia o btDevice e pega o disp conectado pelo socket
                    BluetoothDevice btDevice = socket.getRemoteDevice();
                    //atriui nome do cliente conectado
                    nomeClienteCn = btDevice.getName();
                    //atribui o adress do endereco conectado
                    address = btDevice.getAddress();
                    //ativar serverativo = true;
                    cnServerAtivo = true;
                    //cria o array de bytes pra receber a msg
                    byte[] msgArrayByte = new byte[1024];
                    //enquanto o servidor estiver ativo
                    while (cnServerAtivo){
                        //pega o tamamnho do que foi recebido
                        int tamanhoMsg = inputRec.read(msgArrayByte);
                        //instancia a string com o array de bytes e o tamanho do input
                        msgRec = new String(msgArrayByte,0,tamanhoMsg);
                        //chama a actitity pra poder atualizar os elementos da tela
                       MainActivity.this.runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               //exibe a msg pro usuario
                               Toast.makeText(MainActivity.this,"O dispositivo: "+ nomeClienteCn+
                                    " - "+address+" conectou",Toast.LENGTH_LONG).show();
                               txvNomeClienteCn.setText(nomeClienteCn);
                               msgRec = txvMsg.getText().toString() + msgRec + "\n";
                               //seta no txv a msg recebida
                               txvMsg.setText(msgRec);
                           }
                       });
                        //escreve no output e pega os bytes do texto
                        outputEnvio.write(("Mensagem recebida.").getBytes());
                        //flush dá descarga do output stream. tira do buffer o texto
                        outputEnvio.flush();
                    }
                }
            } catch (IOException e) {
                Toast.makeText(MainActivity.this,"Erro: "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
