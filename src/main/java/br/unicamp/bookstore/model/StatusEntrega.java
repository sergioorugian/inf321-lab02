package br.unicamp.bookstore.model;

import javax.xml.bind.annotation.XmlElement;

public class StatusEntrega {

	@XmlElement(name = "tipo")
	private String tipo;

	@XmlElement(name = "status")
	private String status;

	@XmlElement(name = "descricao")
	private String descricao;
	
	@XmlElement(name = "erro")
	private String erro;
	
	@XmlElement(name = "cidade")
	private String cidade;
	
	@XmlElement(name = "local")
	private String local;

	public String getTipo() {
		return tipo;
	}

	public String getStatus() {
		return status;
	}

	public String getErro(){
		return erro;  
	}
	public String getDescricao() {
		return descricao;
	}

	public String getCidade() {
		return cidade;
	}

	public String getLocal() {
		return local;
	}

}
