package br.com.treinaweb.twprojetos.dao;

public class AlertDAO {
    private String mensagem;
    private String classCss;

    public AlertDAO(String mensagem, String classCss) {
        this.mensagem = mensagem;
        this.classCss = classCss;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getClassCss() {
        return classCss;
    }

    public void setClassCss(String classCss) {
        this.classCss = classCss;
    }
}
