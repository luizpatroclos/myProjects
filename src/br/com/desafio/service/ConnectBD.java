package br.com.desafio.service;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;




public class ConnectBD

{

	private Cluster cluster;

	private Session session;
	

 public Session connect(final String node, final int port)

	{

		this.cluster = Cluster.builder().addContactPoint(node).withPort(port).build();

		return session = cluster.connect();

	}

	
	public Session getSession() {

		return this.session;

	}

	

	public void close() {

		cluster.close();

	}

}
