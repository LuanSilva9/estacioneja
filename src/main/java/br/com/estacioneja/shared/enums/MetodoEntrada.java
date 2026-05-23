package br.com.estacioneja.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MetodoEntrada {
    QR_CODE("QR_CODE", "Esse tipo consiste em, quando você entrar no estacionamento você precisa clicar no card referente ao estacionamento, para que o sistema gere um QR Code e permita sua entrada após a validação."), 
    NFC_RFID("NFC_RFID", "Esse método de entrada requer uma tag, geralmente colocada em algum lugar visivel do automovel, ao chegar no estacionamento  você verá um leitor, ele irá ler sua tag e permitir sua entrada."), 
    GUARITA_SIMPLES("GUARITA_SIMPLES", "Esse método de entrada é basico e requer apenas ter vinculo com o estacionamento, quando você chegar, o(a) colaborador(a) da guarita irá checar sua placa e permitir ou negar sua entrada.");

    String tipo;
    String especificacao;
}
