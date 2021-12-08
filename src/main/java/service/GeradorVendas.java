package service;

import java.math.BigDecimal;
import java.util.Random;

import org.apache.kafka.common.serialization.StringSerializer;

import model.Venda;
import serializer.VendaSerializer;
import utils.Log;


public class GeradorVendas {

	private static Random rand = new Random();
	private static long operacao = 0;
	private static BigDecimal valorIngresso = BigDecimal.valueOf(500);

	public static void main(String[] args) {
		try {
			while (true) {
				Venda venda = geraVenda();
				Producer<StringSerializer, Venda> producer = new Producer<StringSerializer, Venda>(
						"localhost:9092", StringSerializer.class.getName(), VendaSerializer.class.getName());
				
				producer.send("topico_vendas", venda);
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			Log.err(GeradorVendas.class.getName(), e.getMessage());
		}
	}

	private static Venda geraVenda() {

		int quantidadeIngressos = rand.nextInt(10);

		return new Venda(operacao++, rand.nextLong(), quantidadeIngressos,
				valorIngresso.multiply(BigDecimal.valueOf(quantidadeIngressos)));
	}
}
