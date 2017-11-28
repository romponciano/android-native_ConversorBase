package ponciano.romulo.conversordebase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import java.lang.String;

public class MainActivity extends ActionBarActivity {

    // variaveis
    String binario, octal, decimal, hexadecimal;
    Double decimalResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mapeando
        final EditText txtBinario = (EditText) findViewById(R.id.txtBinario);
        final EditText txtOctal = (EditText) findViewById(R.id.txtOctal);
        final EditText txtDecimal = (EditText) findViewById(R.id.txtDecimal);
        final EditText txtHexadecimal = (EditText) findViewById(R.id.txtHexadecimal);
        final RadioButton radBinario = (RadioButton) findViewById(R.id.radBinario);
        final RadioButton radOctal = (RadioButton) findViewById(R.id.radOctal);
        final RadioButton radDecimal = (RadioButton) findViewById(R.id.radDecimal);
        final RadioButton radHexadecimal = (RadioButton) findViewById(R.id.radHexadecimal);
        Button btnCalcular = (Button) findViewById(R.id.btnCalcular);

        // ------- INICIO DA VERIFICACAO DOS RADIO BUTTON's (sem RadioGroup no XML)
        radBinario.setOnClickListener(new View.OnClickListener() {
            // se radBinario for clickado, limpar os outros.
            public void onClick(View v) {
                radOctal.setChecked(false);
                radDecimal.setChecked(false);
                radHexadecimal.setChecked(false);
            }
        });

        radOctal.setOnClickListener(new View.OnClickListener(){
            // se radOctal for clickado, limpar os outros.
            public void onClick(View v) {
                radBinario.setChecked(false);
                radDecimal.setChecked(false);
                radHexadecimal.setChecked(false);
            }
        });

        radDecimal.setOnClickListener(new View.OnClickListener() {
            // se radDecimal for clickado, limpar os outros.
            public void onClick(View v) {
                radBinario.setChecked(false);
                radOctal.setChecked(false);
                radHexadecimal.setChecked(false);
            }
        });

        radHexadecimal.setOnClickListener(new View.OnClickListener() {
            // se radHexadecimal for clickado, limpar os outros.
            public void onClick(View v) {
                radBinario.setChecked(false);
                radOctal.setChecked(false);
                radDecimal.setChecked(false);
            }
        });
        // ------- FIM DA VERIFICACAO DOS RADIO BUTTON's

        // alertDialog
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Erro");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {} });

        // btnCalcular - Iniciar as conversões
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // binario para outros
                if (radBinario.isChecked()) {
                    binario = txtBinario.getText().toString();
                    // verificando se a string binario possui apenas 0's e 1's
                    if (binario.matches("^(0|1){1,}$")) {
                        ParaDecimal(2, binario);
                        // binario para octal
                        octal = DecimalPara(8, decimalResult);
                        txtOctal.setText(octal);
                        // binario para decimal
                        decimal = decimalResult.toString();
                        txtDecimal.setText(decimal);
                        // binario para hexadecimal
                        hexadecimal = DecimalPara(16, decimalResult);
                        txtHexadecimal.setText(hexadecimal);
                    }
                    else {
                        alertDialog.setMessage(
                                "O campo binario deve conter apenas digitos 0 (zero) e 1 (um)");
                        alertDialog.show();
                    }
                }

                // octal para outros
                else if (radOctal.isChecked()) {
                    octal = txtOctal.getText().toString();
                    // verificando se a string octal possui apenas 0's a 7's
                    if(octal.matches("^[0-7]{1,}$")) {
                        ParaDecimal(8, octal);
                        // octal para binario
                        binario = DecimalPara(2, decimalResult);
                        txtBinario.setText(binario);
                        // octal para decimal
                        decimal = decimalResult.toString();
                        txtDecimal.setText(decimal);
                        // octal para hexadecimal
                        hexadecimal = DecimalPara(16, decimalResult);
                        txtHexadecimal.setText(hexadecimal);
                    }
                    else {
                        alertDialog.setMessage(
                                "O campo octal deve conter apenas digitos de 0 (zero) a 7 (sete)");
                        alertDialog.show();
                    }
                }

                // decimal para outros
                else if (radDecimal.isChecked()) {
                    decimal = txtDecimal.getText().toString();
                    // verificando se a string decimal possui apenas algarismos
                    if(decimal.matches("^[0-9]{1,}$")) {
                        // decimal para binario
                        binario = DecimalPara(2, Double.parseDouble(decimal));
                        txtBinario.setText(binario);
                        // decimal para octal
                        octal = DecimalPara(8, Double.parseDouble(decimal));
                        txtOctal.setText(octal);
                        // decimal para hexadecimal
                        hexadecimal = DecimalPara(16, Double.parseDouble(decimal));
                        txtHexadecimal.setText(hexadecimal);
                    }
                    else {
                        alertDialog.setMessage(
                                "O campo decimal deve conter apenas dígitos.");
                        alertDialog.show();
                    }
                }

                // hexa para outros
                else if (radHexadecimal.isChecked()) {
                    hexadecimal = txtHexadecimal.getText().toString();
                    // verificando se a string hexadecimal contém apenas valores válidos
                    if(hexadecimal.matches("^[0-9_A-F_a-f]{1,}$")) {
                        ParaDecimal(16, hexadecimal);
                        // hexadecimal para binario
                        binario = DecimalPara(2, decimalResult);
                        txtBinario.setText(binario);
                        // hexadecimal para octal
                        octal = DecimalPara(8, decimalResult);
                        txtOctal.setText(octal);
                        // hexadecimal para decimal
                        decimal = decimalResult.toString();
                        txtDecimal.setText(decimal);
                    }
                    else {
                        alertDialog.setMessage(
                                "O campo hexadecimal deve conter apenas dígitos e as letras " +
                                        "A, B, C, D, E, F");
                        alertDialog.show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /* action bar three dots. for config menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    // --------------------------- INICIO FUNCOES ---------------------------------------------

    // converte qualquer base até 9 e a base 16, para decimal
    public void ParaDecimal(int base, String palavra) {
        int expo, naux, j, i;
        decimalResult=0.0; expo=0; naux=0;
        j=palavra.length();
        String letra;

        for(i=1; i<=(palavra.length()); i++) {
            if(base==16) {
                letra = palavra.substring(j-1,j);
                if( (letra.equalsIgnoreCase("A")) ) naux=10;
                else if( (letra.equalsIgnoreCase("B")) ) naux=11;
                else if( (letra.equalsIgnoreCase("C")) ) naux=12;
                else if( (letra.equalsIgnoreCase("D")) ) naux=13;
                else if( (letra.equalsIgnoreCase("E")) ) naux=14;
                else if( (letra.equalsIgnoreCase("F")) ) naux=15;
                else naux = Integer.parseInt(letra);
            }
            else {
                letra=palavra.substring(j-1,j);
                naux = Integer.parseInt(letra);
            }

            decimalResult += (Math.pow(base, expo))*naux;
            expo++; j--;
        }
    }

    // converte decimal para qualquer base até 9 e a base 16
    public String DecimalPara(int base, double dec) {
        int decnum, resto;
        String novaPalavra;
        String saida="";

        novaPalavra=""; saida="";
        decnum=(int)dec;
        while(decnum>=1) {
            resto = decnum%base;
            decnum /= base;
            novaPalavra = String.valueOf(resto);
            if(base==16) {
                if(resto==10) novaPalavra="A";
                else if(resto==11) novaPalavra="B";
                else if(resto==12) novaPalavra="C";
                else if(resto==13) novaPalavra="D";
                else if(resto==14) novaPalavra="E";
                else if(resto==15) novaPalavra="F";
            }
            saida = novaPalavra+saida;
        }
        return saida;
    }

    // ------------------------------ FIM FUNCOES ---------------------------------------
}