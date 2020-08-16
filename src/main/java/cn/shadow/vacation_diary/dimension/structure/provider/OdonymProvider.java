package cn.shadow.vacation_diary.dimension.structure.provider;

import java.util.Random;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;

public abstract class OdonymProvider extends Provider {

	public abstract String[] generateFossilOdonym(VocationCityWorldGenerator generator, Odds odds);

	public abstract String[] generateNorthSouthStreetOdonym(VocationCityWorldGenerator generator, int x, int z);

	public abstract String[] generateWestEastStreetOdonym(VocationCityWorldGenerator generator, int x, int z);

	public abstract String generateVillagerName(VocationCityWorldGenerator generator, Odds odds);

	// yep it is a little one... we will make it bigger in a moment
	private final int baseSeed;

	OdonymProvider(int baseSeed) {
		super();
		this.baseSeed = baseSeed;
	}

	Random getRandomFor(int i) {
		return new Random((long) i * (long) Integer.MAX_VALUE + (long) baseSeed);
	}

	public void decaySign(Odds odds, String[] text) {
		for (int i = 0; i < text.length; i++) {
			text[i] = decayLine(odds, text[i]);
		}
	}

	private final static double oddsOfDecay = Odds.oddsExtremelyLikely;

	private String decayLine(Odds odds, String line) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < line.length(); i++) {
			if (odds.playOdds(oddsOfDecay))
				result.append(line.charAt(i));
			else
				result.append(' ');
		}
		return result.toString();
	}

	// Based on work contributed by drew-bahrue
	// (https://github.com/echurchill/CityWorld/pull/2)
	public static OdonymProvider loadProvider(VocationCityWorldGenerator generator, Odds odds) {

		OdonymProvider provider = null;

//		// need something like PhatLoot but for Odonym
//		provider = OdonymProvider_PhatOdonym.loadPhatOdonym();

		// default to stock OreProvider
		if (provider == null) {

//			if (generator.settings.environment == Environment.NETHER)
//				provider = new NameProvider_Nether(random);
//			else if (generator.settings.environment == Environment.THE_END)
//				provider = new NameProvider_TheEnd(random);
//			else
			provider = new OdonymProvider_Normal(odds.getRandomInt());
		}

		return provider;
	}

}
