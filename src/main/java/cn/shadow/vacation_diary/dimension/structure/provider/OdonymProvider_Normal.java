package cn.shadow.vacation_diary.dimension.structure.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cn.shadow.vacation_diary.dimension.VocationCityWorldGenerator;
import cn.shadow.vacation_diary.dimension.support.Odds;
import cn.shadow.vacation_diary.util.RandomNameMaker;

public class OdonymProvider_Normal extends OdonymProvider {

	public OdonymProvider_Normal(int seed) {
		super(seed);
	}

	@Override
	public String[] generateNorthSouthStreetOdonym(VocationCityWorldGenerator generator, int x, int z) {
		int streetN = generateStreetNumber(z);
		String[] result = new String[4];
		result[0] = generateNumericPrefix(streetN, getNorth(), getSouth());
		result[1] = generateNumericName(streetN, getMain());
		result[2] = "Street";
		result[3] = generateStreetBlockNumbers(x);
		return result;
	}

	private String generateStreetBlockNumbers(int i) {
		// TODO need to work on orientation
//		int streetN = generateStreetNumber(i);
//		if (i < 0)
//			return Math.abs(streetN * 100) + "<->" + Math.abs((streetN + 1) * 100);
//		else
//			return Math.abs((streetN - 1) * 100) + "<->" + Math.abs(streetN * 100);
		return "";
	}

	private int generateStreetNumber(int chunkN) {
		return (Math.max(0, Math.abs(chunkN - 2) + 2) / 5) * (chunkN < 0 ? -1 : 1); // normalize it and then re-sign it
	}

	private String generateNumericName(int streetN, String central) {
		if (streetN == 0)
			return central;
		else {
			streetN = Math.abs(streetN);
			switch (streetN % 10) {
			case 1:
				return streetN + "st";
			case 2:
				return streetN + "nd";
			case 3:
				return streetN + "rd";
			// case 4:
			// return streetN + "th";
			// case 5:
			// return streetN + "th";
			// case 6:
			// return streetN + "th";
			// case 7:
			// return streetN + "th";
			// case 8:
			// return streetN + "th";
			// case 9:
			// return streetN + "th";
			// case 9:
			// return streetN + "th";
			default:
				return streetN + "th";
			}
		}
	}

	private String generateNumericPrefix(int streetN, String negative, String positive) {
		if (streetN == 0)
			return "";
		else
			return streetN < 0 ? negative : positive;
	}

	@Override
	public String[] generateWestEastStreetOdonym(VocationCityWorldGenerator generator, int x, int z) {
		int streetN = generateStreetNumber(x);
		Random random = getRandomFor(streetN);
		String[] result = new String[4];
		result[0] = ""; //generateStreetNamedPrefix(random, streetN, getWest(), getEast());
		result[1] = generateStreetNamedName(random, streetN, getCentral());
		result[2] = ""; //getSuffixPart(random, streetN);
		result[3] = generateStreetBlockNumbers(z);
		return result;
	}

//	private String generateStreetNamedPrefix(Random random, int streetN, String negative, String positive) {
//		if (streetN == 0)
//			return "";
//		else
//			return (streetN < 0 ? negative : positive) + getPrefixPart(random);
//	}

//	private String getPrefixPart(Random random) {
//		int pick = random.nextInt(streetPrefixes.size() * 2);
//		if (pick < streetPrefixes.size())
//			return " " + getName(streetPrefixes, pick, "");
//		else
//			return "";
//	}

	private String generateStreetNamedName(Random random, int streetN, String central) {
		if (streetN == 0)
			return central;
		else {
			return getStartingPart(random) + getEndingPart(random);
		}
	}

	private String getStartingPart(Random random) {
		return getName(streetStarts, random, "Van");
	}

	private String getEndingPart(Random random) {
		int pick = random.nextInt(streetEnds.size() * 3 / 2);
		if (pick < streetEnds.size())
			return getName(streetEnds, pick, "Brocklin");
		else
			return "";
	}

//	private String getSuffixPart(Random random, int streetN) {
//		if (streetN == 0)
//			return getName(streetSuffixes, 0, "Street");
//		else
//			return getName(streetSuffixes, random, "Street");
//	}

	@Override
	public String[] generateFossilOdonym(VocationCityWorldGenerator generator, Odds odds) {
		String[] result = new String[4];

		String prefix = getName(fossilPrefixes, odds, "Dino");
//		String middle = getFossilMiddlePart(odds);
		String suffix = getName(fossilSuffixes, odds, "saur");

//		result[1] = smartConcat(odds, smartConcat(odds, prefix, middle), suffix);
		result[1] = smartConcat(odds, prefix, suffix);
		return result;
	}

	private String smartConcat(Odds odds, String first, String second) {
		return first + second;
	}

	@Override
	public String generateVillagerName(VocationCityWorldGenerator generator, Odds odds) {
		return RandomNameMaker.getFullName(odds);		
	}

	private List<String> streetTerms = createList(termDeclaration, termNorth, termMain, termSouth, termWest,
			termCentral, termEast);

//	private List<String> streetPrefixes = createList("Mount", "Fort", "New", "Upper", "Lower", "Lake", "Ben", "Old",
//			"Ole", "Saint");

	private List<String> streetStarts = createList(
			"人民","解放","胜利","民生","东风","中正","中山","长江","黄河","长城",
			"北京","上海","广州","深圳","重庆","三亚","天津",
			"阿坝","阿拉善","阿里","安康","安庆","鞍山","安顺","安阳","澳门",
			"北京","白银","保定","宝鸡","保山","包头","巴中","北海","蚌埠","本溪","毕节","滨州","百色","亳州",
			"重庆","成都","长沙","长春","沧州","常德","昌都","长治","常州","巢湖","潮州","承德","郴州","赤峰","池州","崇左","楚雄","滁州","朝阳",
			"大连","东莞","大理","丹东","大庆","大同","大兴安岭","德宏","德阳","德州","定西","迪庆","东营",
			"鄂尔多斯","恩施","鄂州",
			"福州","防城港","佛山","抚顺","抚州","阜新","阜阳",
			"广州","桂林","贵阳","甘南","赣州","甘孜","广安","广元","贵港","果洛",
			"杭州","哈尔滨","合肥","海口","呼和浩特","海北","海东","海南","海西","邯郸","汉中","鹤壁","河池","鹤岗","黑河","衡水","衡阳","河源","贺州","红河","淮安","淮北","怀化","淮南","黄冈","黄南","黄山","黄石","惠州","葫芦岛","呼伦贝尔","湖州","菏泽",
			"济南","佳木斯","吉安","江门","焦作","嘉兴","嘉峪关","揭阳","吉林","金昌","晋城","景德镇","荆门","荆州","金华","济宁","晋中","锦州","九江","酒泉",
			"昆明","开封",
			"兰州","拉萨","来宾","莱芜","廊坊","乐山","凉山","连云港","聊城","辽阳","辽源","丽江","临沧","临汾","临夏","临沂","林芝","丽水","六安","六盘水","柳州","陇南","龙岩","娄底","漯河","洛阳","泸州","吕梁",
			"马鞍山","茂名","眉山","梅州","绵阳","牡丹江",
			"南京","南昌","南宁","宁波","南充","南平","南通","南阳","那曲","内江","宁德","怒江",
			"盘锦","攀枝花","平顶山","平凉","萍乡","莆田","濮阳",
			"青岛","黔东南","黔南","黔西南","庆阳","清远","秦皇岛","钦州","齐齐哈尔","泉州","曲靖","衢州",
			"日喀则","日照",
			"上海","深圳","苏州","沈阳","石家庄","三门峡","三明","三亚","商洛","商丘","上饶","山南","汕头","汕尾","韶关","绍兴","邵阳","十堰","朔州","四平","绥化","遂宁","随州","宿迁","宿州",
			"天津","太原","泰安","泰州","台州","唐山","天水","铁岭","铜川","通化","通辽","铜陵","铜仁","台湾",
			"暂无",
			"暂无",
			"武汉","乌鲁木齐","无锡","威海","潍坊","文山","温州","乌海","芜湖","乌兰察布","武威","梧州",
			"厦门","西安","西宁","襄樊","湘潭","湘西","咸宁","咸阳","孝感","邢台","新乡","信阳","新余","忻州","西双版纳","宣城","许昌","徐州","香港","锡林郭勒","兴安",
			"银川","雅安","延安","延边","盐城","阳江","阳泉","扬州","烟台","宜宾","宜昌","宜春","营口","益阳","永州","岳阳","榆林","运城","云浮","玉树","玉溪","玉林"
	);
			

	private List<String> streetEnds = createList("路","街","道","巷","弄","村","镇","旗","坊",
			"桥","山","径");

//	private List<String> streetSuffixes = createList("Street", "Avenue", "Road", "Lane", "Way", "Pass", "Trail",
//			"Court", "Route", "Boulevard", "Grade", "Ridge", "Parkway", "Promenade", "Bypass", "Quay", "Motorway",
//			"Vale", "Grove", "Gardens", "Fairway", "Bend", "Heights", "View", "Place", "Plaza", "Hill", "Pike", "Alley",
//			"Gate", "Knoll", "Mews", "Terrace", "Cove");

	private List<String> fossilPrefixes = createList("Archea", "Amphel", "Belo", "Bronto", "Camel", "Carno", "Dein",
			"Dino", "Eoabel", "Eshano", "Fuku", "Futaba", "Gala", "Glypto", "Hadro", "Hesper", "Iguano", "Ichthyo",
			"Jinta", "Jurave", "Kentro", "Krito", "Labo", "Lepto", "Majun", "Melan", "Nano", "Ningy", "Omni", "Othni",
			"Pachy", "Parro", "Quaesit", "Qantas", "Rinch", "Rug", "Shamo", "Sauro", "Tachi", "Tyranno", "Tro", "Utah",
			"Ultra", "Vari", "Veloci", "Wakino", "Wintono", "Xeno", "Xuwu", "Yang", "Yul", "Zana", "Zephyro");

//	private List<String> fossilMiddles = createList(
//			"cera", "ingo", "oro", "uan", "ara", "cephal", "saur", "pose", "tarso"
//			);

	private List<String> fossilSuffixes = createList("鸟","龙","兽","象","龟","翼龙","鱼龙");

	private List<String> createList(String... items) {
		return new ArrayList<>(Arrays.asList(items));
	}

	private String getName(List<String> values, Random random, String value) {
		if (values == null || values.size() == 0)
			return value;
		else
			return getName(values, random.nextInt(values.size()), value);
	}

	private String getName(List<String> values, Odds odds, String value) {
		if (values == null || values.size() == 0)
			return value;
		else
			return getName(values, odds.getRandomInt(values.size()), value);
	}

	private String getName(List<String> values, int index, String value) {
		if (values == null || values.size() == 0 || index < 0 || index >= values.size())
			return value;
		else
			return values.get(index);
	}

	private static final String termDeclaration = "These strings represents cardinal prefixes and central roads";
	private static final String termNorth = "North";
	private static final String termMain = "Main";
	private static final String termSouth = "South";

	private static final String termWest = "West";
	private static final String termCentral = "Central";
	private static final String termEast = "East";

	private String getTerm(int index, String value) {
		if (streetTerms.size() > index)
			return streetTerms.get(index);
		else
			return value;
	}

	@SuppressWarnings("unused")
	private String getNorth() {
		return getTerm(1, termNorth);
	}
	
	@SuppressWarnings("unused")
	private String getMain() {
		return getTerm(2, termMain);
	}

	@SuppressWarnings("unused")
	private String getSouth() {
		return getTerm(3, termSouth);
	}

	@SuppressWarnings("unused")
	private String getWest() {
		return getTerm(4, termWest);
	}

	@SuppressWarnings("unused")
	private String getCentral() {
		return getTerm(5, termCentral);
	}

	@SuppressWarnings("unused")
	private String getEast() {
		return getTerm(6, termEast);
	}
}
