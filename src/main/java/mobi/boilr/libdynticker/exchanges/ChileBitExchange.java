package mobi.boilr.libdynticker.exchanges;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import mobi.boilr.libdynticker.core.Exchange;
import mobi.boilr.libdynticker.core.Pair;

public final class ChileBitExchange extends Exchange {
	private static final List<Pair> pairs;
	static {
		List<Pair> tempPairs = new ArrayList<Pair>();
		tempPairs.add(new Pair("BTC", "VEF"));
		tempPairs.add(new Pair("BTC", "BRL"));
		tempPairs.add(new Pair("BTC", "PKR"));
		tempPairs.add(new Pair("BTC", "CLP"));
		tempPairs.add(new Pair("BTC", "VND"));
		pairs = Collections.unmodifiableList(tempPairs);
	}

	public ChileBitExchange(long expiredPeriod) {
		super("ChileBit", expiredPeriod);
	}

	@Override
	protected List<Pair> getPairsFromAPI() throws IOException {
		return pairs;
	}

	@Override
	protected String getTicker(Pair pair) throws IOException {
		if(!pairs.contains(pair))
			throw new IOException("Invalid pair: " + pair);
		// https://api.blinktrade.com/api/v1/VEF/ticker?crypto_currency=BTC
		JsonNode node = readJsonFromUrl("https://api.blinktrade.com/api/v1/" + pair.getExchange()
				+ "/ticker?crypto_currency=" + pair.getCoin());
		return parseTicker(node, pair);
	}

	@Override
	public String parseTicker(JsonNode node, Pair pair) throws IOException {
		return node.get("last").asText();
	}

}
