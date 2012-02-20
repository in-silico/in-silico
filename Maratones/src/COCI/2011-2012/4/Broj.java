import java.util.Scanner;


public class Broj 
{
	
	static int[] criba(int i) 
	{
		boolean[] criba = new boolean[i];
		criba[0] = true;
		criba[1] = true;
		int cuenta = 0;
		for(int j = 2; j < i; j++)
			if(!criba[j])
			{
				cuenta++;
				for(int k = j * j; k < i; k += j)
					criba[k] = true;
			}
		int[] primos = new int[cuenta];
		int actual = 0;
		for(int j = 0; j < i; j++)
			if(!criba[j])
				primos[actual++] = j;
		return primos;
	}
	
	
	static void generar()
	{
		
		int[] primos = criba(31650);
		int indice = -1;
		for(int i = 0; i < primos.length; i++)
			if(primos[i] > 1000)
			{
				indice = i;
				break;
			}
		int actual = 0;
		System.out.print("int[][] memoria = new int[][]{");
		for(int p = primos[actual]; actual < indice; p = primos[++actual])
		{
			int cuenta = 1;
			for(long i = ((long) p) * p; i <= mayor; i += p)
			{
				boolean paila = false;
				for(int j = 0; j < actual; j++)
					if(i % primos[j] == 0)
					{
						paila = true;
						break;
					}
				if(!paila)
					cuenta++;
			}	
			System.out.print("{" + cuenta);
			int actualP = cuenta / 15;
			int cuentaActual = 1;
			for(long i = ((long) p) * p; i <= mayor; i += p)
			{
				boolean paila = false;
				for(int j = 0; j < actual; j++)
					if(i % primos[j] == 0)
					{
						paila = true;
						break;
					}
				if(!paila)
				{
					cuentaActual++;
					if(cuentaActual == actualP || cuentaActual == cuenta)
					{
						actualP += cuenta / 15;
						System.out.print(", " + i);
					}
				}
			}
			System.out.println("},");
				
		}
	}
	
	static int[][] memoria = new int[][]{
			{500000000, 66666666, 133333332, 199999998, 266666664, 333333330, 399999996, 466666662, 533333328, 599999994, 666666660, 733333326, 799999992, 866666658, 933333324, 999999990, 1000000000},
			{166666667, 66666663, 133333329, 199999995, 266666661, 333333327, 399999993, 466666659, 533333325, 599999991, 666666657, 733333323, 799999989, 866666655, 933333321, 999999987, 999999999},
			{66666667, 66666655, 133333315, 199999975, 266666635, 333333295, 399999955, 466666615, 533333275, 599999935, 666666595, 733333255, 799999915, 866666575, 933333235, 999999895, 999999995},
			{38095238, 66666649, 133333291, 199999933, 266666603, 333333259, 399999901, 466666543, 533333213, 599999869, 666666511, 733333153, 799999823, 866666479, 933333121, 999999763, 999999973},
			{20779221, 66666611, 133333321, 199999943, 266666587, 333333209, 399999853, 466666519, 533333141, 599999807, 666666451, 733333073, 799999717, 866666383, 933333049, 999999671, 999999979},
			{15984017, 66666613, 133333369, 200000021, 266666647, 333333299, 399999977, 466666603, 533333281, 599999933, 666666559, 733333211, 799999889, 866666567, 933333193, 999999871, 999999949},
			{11282835, 66666503, 133333363, 200000189, 266666471, 333333331, 399999953, 466666643, 533333299, 599999887, 666666577, 733333301, 800000059, 866666783, 933333133, 999999857},
			{9501332, 66666763, 133333127, 199999757, 266666273, 333333017, 399999989, 466666391, 533332907, 599999537, 666666167, 733332949, 799999427, 866666209, 933333029, 999999773, 999999887},
			{7435827, 66666121, 133333139, 199999697, 266666347, 333332767, 399999233, 466665791, 533332763, 599998769, 666665281, 733332161, 799998811, 866665001, 933331513, 999998071, 999999911},
			{5640970, 66666389, 133333561, 199998529, 266665759, 333332641, 399998653, 466665361, 533332243, 599999299, 666664441, 733331033, 799998379, 866664797, 933331853, 999998213, 999999953},
			{5095069, 66668011, 133331899, 199998763, 266666557, 333330817, 399998983, 466664669, 533332091, 599999327, 666666191, 733331939, 799997687, 866664427, 933331787, 999999023, 999999829},
			{4131144, 66669523, 133335827, 200000873, 266665697, 333331483, 399996973, 466664239, 533330617, 599996477, 666664409, 733329899, 799997609, 866664949, 933329477, 999997631, 999999851},
			{3627361, 66674159, 133339831, 200007307, 266670601, 333336191, 400003831, 466666633, 533336159, 599999371, 666668077, 733333093, 800001143, 866668783, 933334373, 999999799, 999999881},
			{3374294, 66674639, 133347257, 200013769, 266679851, 333345073, 400004963, 466674227, 533341943, 600005101, 666668689, 733336319, 800002057, 866665817, 933332329, 999995831, 999999787},
			{3015293, 66671333, 133352113, 200021707, 266688857, 333352247, 400020713, 466684949, 533345237, 600011353, 666675683, 733339073, 800002369, 866669143, 933330653, 999997333, 999999683},
			{2616983, 66656563, 133352717, 200031593, 266698067, 333365707, 400024973, 466692719, 533358451, 600019201, 666681541, 733346849, 800010779, 866672377, 933333551, 999997057, 999999919},
			{2306412, 66629467, 133330501, 200016077, 266697287, 333370237, 400040827, 466707287, 533368201, 600033127, 666694867, 733359793, 800015987, 866678671, 933340883, 999995071, 999999673},
			{2192861, 66605717, 133301897, 199985267, 266676811, 333353227, 400032571, 466699837, 533361979, 600030953, 666689923, 733351699, 800016769, 866667199, 933333001, 999994777, 999999901},
			{1963655, 66569257, 133251409, 199947899, 266635009, 333325067, 400004807, 466675301, 533357453, 600016289, 666692947, 733343609, 800022143, 866675083, 933337001, 999997177, 999999991},
			{1825279, 66524089, 133198201, 199890347, 266586611, 333264557, 399962951, 466646293, 533320973, 599998351, 666659683, 733337629, 799990441, 866673641, 933336109, 999998009, 999999997},
			{1750220, 66491101, 133142729, 199831003, 266521613, 333214121, 399902249, 466596217, 533273249, 599954807, 666635927, 733317631, 799981961, 866645707, 933323177, 999996121, 999999479},
			{1595164, 66460883, 133077001, 199761691, 266438639, 333130913, 399827453, 466528891, 533227801, 599914229, 666601447, 733289771, 799973039, 866649197, 933314927, 999997247, 999999617},
			{1499128, 66438263, 133017377, 199673183, 266357209, 333054847, 399750991, 466442321, 533143777, 599839589, 666544199, 733233703, 799935823, 866623999, 933306697, 999992051, 999999853},
			{1381338, 66430579, 132961817, 199592557, 266267797, 332946953, 399654767, 466349231, 533059181, 599778031, 666485311, 733184581, 799872637, 866579383, 933303929, 999996079, 999999817},
			{1253380, 66445097, 132915511, 199513771, 266175857, 332839883, 399550663, 466265323, 532972417, 599674661, 666401737, 733113487, 799828147, 866545523, 933276479, 999990169, 999998317},
			{1191537, 66476281, 132893881, 199470253, 266105003, 332769649, 399463787, 466164389, 532886201, 599608619, 666337501, 733055273, 799781933, 866508593, 933241919, 999989183, 999996859},
			{1157070, 66527597, 132891527, 199417991, 266039833, 332705141, 399406499, 466113419, 532820339, 599560631, 666298657, 733008667, 799757611, 866511911, 933259001, 999999499},
			{1103283, 66585137, 132915721, 199409801, 266009383, 332655403, 399335021, 466045241, 532741337, 599490077, 666238817, 732974503, 799727737, 866492741, 933233563, 999996427, 999998567},
			{1073186, 66653173, 132937163, 199407979, 265986269, 332598131, 399284113, 465975109, 532687033, 599425553, 666178243, 732920033, 799670107, 866434351, 933217561, 999990743, 999999899},
			{1026045, 66750569, 133010831, 199442627, 265988101, 332581939, 399251261, 465945217, 532653637, 599390081, 666157939, 732905231, 799658399, 866426257, 933218749, 999999941},
			{905328, 66911093, 133192901, 199534399, 266011787, 332610841, 399240629, 465925027, 532608917, 599334209, 666069661, 732862771, 799629973, 866431211, 933207049, 999996857, 999999397},
			{871079, 67041739, 133306517, 199634437, 266083663, 332625899, 399241757, 465925211, 532605259, 599332729, 666050243, 732805747, 799605529, 866418673, 933164483, 999988369, 999998849},
			{826895, 67187951, 133471291, 199804499, 266208947, 332724913, 399297049, 465927547, 532624901, 599326639, 666082903, 732806561, 799615433, 866373341, 933201667, 999993277, 999999031},
			{809332, 67293097, 133628623, 199966651, 266351383, 332834527, 399371603, 465974843, 532673159, 599364247, 666121499, 732812309, 799625161, 866410213, 933213613, 999986989, 999997553},
			{749910, 67463177, 133856683, 200207873, 266599889, 333019619, 399518617, 466128769, 532759483, 599424467, 666111503, 732853073, 799626827, 866401177, 933199367, 999997259},
			{735214, 67592281, 134031979, 200383493, 266779099, 333183463, 399659401, 466215671, 532818449, 599461997, 666163529, 732893449, 799619141, 866404327, 933176527, 999993121, 999997651},
			{702687, 67761671, 134255567, 200617583, 267011627, 333428279, 399895799, 466385299, 532966801, 599595089, 666218353, 732944923, 799671493, 866411251, 933207529, 999982769, 999999097},
			{672706, 67910527, 134467013, 200876147, 267290497, 333670291, 400148537, 466593857, 533130131, 599766161, 666342533, 733035613, 799709459, 866459263, 933207763, 999998969, 999999947},
			{652714, 68064691, 134663957, 201086537, 267543853, 333926687, 400396027, 466863029, 533342389, 599959691, 666505517, 733124489, 799804249, 866484343, 933215539, 999996167, 999999841},
			{626428, 68191237, 134904881, 201371827, 267819397, 334197767, 400665059, 467116781, 533610023, 600091501, 666681623, 733228841, 799866019, 866519113, 933211997, 999981347, 999996917},
			{602043, 68352761, 135146611, 201673751, 268100293, 334516453, 400986671, 467438989, 533865889, 600369401, 666926971, 733486331, 800045333, 866678441, 933297587, 999985111, 999999073},
			{592091, 68468137, 135342569, 201884323, 268337749, 334777057, 401187767, 467639021, 534076519, 600538633, 667075681, 733603679, 800159551, 866737867, 933315821, 999978121, 999996583},
			{557985, 68595167, 135591091, 202232519, 268700137, 335150947, 401541401, 468005581, 534450661, 600869383, 667331653, 733827539, 800320751, 866889217, 933421393, 999999409},
			{549262, 68691209, 135782641, 202423997, 268924463, 335374363, 401765977, 468216649, 534621001, 601031143, 667422757, 733984597, 800435269, 866916049, 933479047, 999988777, 999999971},
			{535249, 68825693, 135953443, 202626517, 269187301, 335627521, 401999579, 468453983, 534837073, 601285961, 667663141, 734098633, 800562493, 867079937, 933516217, 999991897, 999999383},
			{527101, 68925839, 136117393, 202834133, 269406001, 335872001, 402254023, 468691367, 535063439, 601468943, 667853353, 734253683, 800709733, 867151853, 933586013, 999997487, 999999079},
			{494393, 69082877, 136363603, 203124847, 269706319, 336191153, 402603403, 468956573, 535336751, 601650253, 668083181, 734373473, 800797961, 867111463, 933588701, 999984493, 999997997},
			{465366, 69238601, 136566761, 203395847, 270037613, 336501871, 402978617, 469323793, 535670753, 602015929, 668360659, 734627339, 801033617, 867307433, 933617821, 999989311, 999999569},
			{454908, 69311953, 136739579, 203615141, 270193787, 336716137, 403174019, 469517947, 535854611, 602129531, 668458477, 734705249, 801076417, 867318649, 933633067, 999992431, 999998333},
			{448777, 69384481, 136857499, 203782291, 270393353, 336941669, 403402507, 469722739, 536036101, 602334349, 668592751, 734844283, 801170011, 867415589, 933684067, 999987811, 999999719},
			{438943, 69455669, 136988389, 203978219, 270657227, 337186649, 403600037, 469900187, 536231093, 602500021, 668719087, 735023431, 801177257, 867474611, 933742141, 999977051, 999999419},
			{425858, 69489967, 137178113, 204206141, 270851291, 337402753, 403828979, 470145743, 536475413, 602779271, 668958371, 735245977, 801372497, 867627599, 933794749, 999969547, 999999661},
			{420348, 69564409, 137323969, 204339803, 271071257, 337594487, 404002037, 470342107, 536655667, 602935969, 669094807, 735344261, 801469841, 867744841, 933845357, 999992627, 999998893},
			{401600, 69581467, 137464417, 204549187, 271355849, 337861813, 404296493, 470606677, 536854111, 603142207, 669234523, 735447821, 801583309, 867650023, 933882899, 999981239, 999999311},
			{390386, 69636977, 137553853, 204697159, 271540289, 338093009, 404476109, 470791361, 537045961, 603330887, 669425633, 735495193, 801682459, 867765383, 933858587, 999965669, 999999593},
			{379713, 69704731, 137669717, 204898303, 271708193, 338244037, 404688883, 471037997, 537206167, 603458497, 669637187, 735698053, 801811519, 867801901, 933990059, 999982019, 999994117},
			{369568, 69692789, 137798747, 205042291, 271926989, 338458759, 404892613, 471268901, 537453661, 603623357, 669842011, 735822869, 801875819, 867879811, 933881651, 999974413, 999997547},
			{365236, 69706891, 137857429, 205196051, 272068553, 338640787, 405072643, 471413443, 537595979, 603750331, 669949669, 735913237, 802024771, 867940643, 933865729, 999993523, 999998401},
			{355706, 69676303, 137910821, 205294949, 272245849, 338769061, 405225239, 471542363, 537723203, 603869141, 670023943, 736019747, 802002809, 867976453, 933927383, 999962521, 999997423},
			{349098, 69687157, 138041531, 205420273, 272356159, 338916629, 405406849, 471707113, 537899473, 603983929, 670171231, 736181503, 802055209, 868050307, 933943121, 999989923, 999994981},
			{345178, 69670921, 138146167, 205517713, 272480041, 339043339, 405589091, 471880709, 538068749, 604152079, 670271633, 736270063, 802155293, 868200701, 933951223, 999964369, 999997763},
			{331916, 69669833, 138228317, 205747237, 272635621, 339413251, 405831077, 472185029, 538381933, 604429993, 670480397, 736572407, 802486273, 868351501, 934204423, 999971789, 999999331},
			{315308, 69551771, 138273721, 205896611, 272846557, 339631951, 406063681, 472367699, 538615843, 604663823, 670687243, 736689787, 802629089, 868444363, 934339457, 999969917, 999998161},
			{309980, 69486419, 138260959, 205969391, 272976829, 339808241, 406218559, 472485817, 538786663, 604813207, 670808029, 736815913, 802671407, 868590967, 934330147, 999974783, 999995309},
			{306759, 69427469, 138316891, 206048839, 273070277, 339863851, 406265549, 472546429, 538854853, 604830871, 670855091, 736846133, 802738267, 868518347, 934269631, 999967079, 999999631},
			{301644, 69371963, 138252259, 206075677, 273178237, 339963163, 406386709, 472710083, 538914899, 604978333, 670940327, 736851601, 802733077, 868544813, 934283639, 999960967, 999999641},
			{287582, 69219713, 138366937, 206057099, 273249437, 340042589, 406578223, 472931807, 539096059, 605200069, 671096873, 737002283, 802840169, 868621123, 934231943, 999986417, 999994361},
			{281332, 69119711, 138340859, 206094709, 273356539, 340163419, 406685197, 472976467, 539287283, 605308279, 671267941, 737069213, 802912273, 868621207, 934309247, 999987851, 999999983},
			{272114, 68904137, 138224327, 206215507, 273477293, 340346969, 406868951, 473242417, 539334119, 605489669, 671325979, 737207399, 802975003, 868750241, 934345733, 999942613, 999988417},
			{269561, 68788249, 138204349, 206209093, 273528401, 340344451, 406954591, 473302981, 539472683, 605570491, 671401663, 737251681, 803034691, 868790479, 934410157, 999962827, 999999821},
			{265508, 68612963, 138206207, 206255429, 273602887, 340475207, 407096191, 473404417, 539497313, 605650219, 671527079, 737352401, 803050643, 868843489, 934425241, 999958279, 999999227},
			{260049, 68438683, 138133507, 206248013, 273605747, 340514731, 407071177, 473489767, 539601053, 605666387, 671559401, 737385641, 803093411, 868856467, 934366787, 999976909, 999999167},
			{253408, 68213923, 138047417, 206285929, 273689149, 340519849, 407167049, 473511107, 539782499, 605809469, 671731477, 737505217, 803239321, 868935257, 934540177, 999947651, 999993893},
			{248382, 68030351, 137946217, 206216407, 273580207, 340531469, 407299961, 473599219, 539849987, 605882923, 671774119, 737572811, 803244683, 868903127, 934515319, 999949963, 999998453},
			{243542, 67801963, 137790377, 206129383, 273639137, 340606921, 407382931, 473692013, 540002611, 606029717, 671918867, 737544991, 803324231, 868933679, 934529483, 999979751, 999991879},
			{240140, 67584563, 137610751, 206050553, 273631669, 340621433, 407463359, 473795129, 539968337, 606035071, 671886559, 737607061, 803357437, 868919377, 934612303, 999972019, 999994999},
			{235563, 67296611, 137458207, 205940101, 273638549, 340639909, 407425763, 473678687, 540043643, 605983033, 671955877, 737621411, 803273719, 868943921, 934557329, 999991019, 999999577},
			{229952, 67006057, 137303641, 205829017, 273563569, 340654187, 407350981, 473618221, 540077609, 606035189, 671957833, 737662921, 803275111, 868938911, 934503461, 999982259, 999998933},
			{226875, 66706751, 137165659, 205794403, 273485609, 340636267, 407393143, 473712929, 540101687, 606042127, 671984171, 737818747, 803454427, 868917677, 934511653, 999993349},
			{221630, 66260863, 136927883, 205670149, 273515069, 340558349, 407471567, 473899711, 540132353, 606142499, 672089659, 737888761, 803324671, 868956901, 934438619, 999975143, 999998047},
			{215549, 65851297, 136641347, 205519081, 273428087, 340495741, 407322889, 473881039, 539949797, 606221351, 672100721, 737874503, 803340739, 868888261, 934419023, 999930511, 999996713},
			{213832, 65552647, 136527353, 205449263, 273380981, 340476593, 407376019, 473920121, 540013753, 606194111, 672226277, 737857651, 803422507, 868915793, 934524433, 999962147, 999995827},
			{208161, 65201249, 136302457, 205203841, 273250121, 340473191, 407320429, 473995267, 540227899, 606326059, 672217339, 737989663, 803611999, 869038661, 934538593, 999967841, 999998873},
			{206558, 64972949, 136101859, 205180081, 273148091, 340395589, 407301017, 473976089, 540228553, 606207361, 672258913, 738008231, 803649299, 869183849, 934572911, 999944653, 999995747},
			{203068, 64678309, 135821771, 205074899, 272947811, 340386113, 407229131, 474022981, 540141649, 606257683, 672275381, 737991047, 803677739, 869232731, 934575247, 999958151, 999998539},
			{200575, 64426819, 135521231, 204879083, 272768833, 340282919, 407152883, 473979433, 540123763, 606302647, 672128903, 738073883, 803705219, 869099107, 934502741, 999936499, 999992317},
			{197241, 64027849, 135285047, 204536113, 272579369, 340230199, 407111443, 473896601, 540047771, 606292333, 672175001, 738085507, 803759839, 869083951, 934526599, 999951287, 999996187},
			{193167, 63731849, 134905943, 204216391, 272467513, 340027651, 407036647, 473795207, 540033701, 606215527, 672250199, 738206267, 803800391, 869199833, 934555403, 999957587, 999998717},
			{190920, 63460799, 134544233, 204027997, 272345431, 339927109, 406923317, 473729593, 540166147, 606398939, 672285059, 738305791, 803946659, 869321069, 934714841, 999991519},
			{189541, 63246263, 134275093, 203815841, 272137973, 339837833, 406913569, 473673539, 540098297, 606359153, 672314429, 738326191, 803977739, 869360747, 934749311, 999996197, 999997123},
			{187325, 63048269, 133846403, 203442479, 271976597, 339635557, 406718239, 473590771, 540110251, 606221573, 672180653, 738075287, 803820481, 869340581, 934687891, 999967019, 999999709},
			{182012, 62599073, 133275523, 203145337, 271776457, 339480233, 406576637, 473529341, 539973347, 606175937, 672093043, 737986199, 803753857, 869361529, 934687549, 999982913, 999994409},
			{178461, 62346227, 132723571, 202806767, 271409483, 339262219, 406373741, 473246633, 539842909, 606092441, 672039059, 737767501, 803751131, 869314967, 934586603, 999963431, 999986807},
			{176505, 62092351, 132311243, 202516387, 271170953, 339061523, 406239161, 473201741, 539874631, 606010367, 672204041, 737952869, 803683039, 869477039, 934766291, 999993677},
			{173125, 61777697, 131805361, 202086517, 270797819, 338791559, 406039793, 472916771, 539615107, 605785501, 671999807, 737757029, 803566147, 869283449, 934585583, 999935621, 999999493},
			{171253, 61548589, 131321731, 201578759, 270449519, 338528557, 405879251, 472715879, 539536411, 605568239, 671905891, 737640949, 803536967, 869139233, 934503077, 999912191, 999998707},
			{168765, 61369621, 130803329, 201157309, 270131899, 338321611, 405689797, 472616171, 539485537, 605589367, 671940571, 737839783, 803546593, 869259511, 934804459, 999996161},
			{164376, 61013789, 130032743, 200695973, 269736809, 337977389, 405536501, 472417271, 539165707, 605622383, 671596613, 737836553, 803453377, 869089999, 934830821, 999966241, 999998543},
			{163311, 60802411, 129674189, 200364961, 269507653, 337727773, 405303557, 472277891, 538994909, 605556073, 671641307, 737674241, 803387099, 869153303, 934694617, 999963971, 999996397},
			{157344, 60395617, 128869987, 199362287, 268740127, 336961309, 404873039, 471880217, 538804081, 605145829, 671412919, 737467937, 803132353, 868898477, 934464431, 999924349, 999994679},
			{155214, 60113659, 128293927, 198934601, 268353277, 336674671, 404364827, 471715843, 538550491, 605089759, 671194709, 737387179, 803232851, 868919893, 934438459, 999926393, 999999691},
			{152009, 59859119, 127673869, 198159991, 267783877, 336294877, 404047243, 471423077, 538239683, 604885847, 670957187, 737165549, 803122147, 868770167, 934343549, 999896879, 999999367},
			{150006, 59651539, 127216043, 197667611, 267381649, 335938159, 403778533, 471136979, 538102451, 604830337, 670854473, 737190511, 803152717, 868815407, 934476971, 999951619, 999998911},
			{148064, 59437171, 126795391, 197113549, 266991301, 335480693, 403506919, 470832137, 537825059, 604779289, 670840189, 737035373, 802999543, 868766839, 934558033, 999899717, 999998723},
			{147187, 59276081, 126419971, 196607291, 266662139, 335186707, 403216789, 470633617, 537603923, 604493147, 670769117, 736836101, 802957901, 868614907, 934346143, 999956327, 999998581},
			{145295, 58979209, 126038149, 195983243, 266050661, 334857911, 402896597, 470346743, 537371063, 604218821, 670624597, 736809959, 802920311, 868588681, 934486697, 999964657, 999999277},
			{142472, 58789811, 125407267, 195146389, 265220101, 334403921, 402465397, 469936351, 537227683, 603980149, 670542427, 736808857, 803047111, 868649057, 934625509, 999987959, 999997351},
			{140695, 58593737, 124911299, 194438177, 264612611, 333839431, 402182681, 469651849, 536909909, 603738637, 670298143, 736644169, 802819411, 868554647, 934362229, 999918379, 999993097},
			{138954, 58359971, 124575827, 193860959, 264094907, 333354881, 401725939, 469357831, 536520107, 603557791, 670073147, 736368071, 802637837, 868521847, 934268087, 999918487, 999995159},
			{138173, 58174997, 124288603, 193309847, 263569151, 332801947, 401282291, 469054657, 536252467, 603308441, 669818707, 736208773, 802507487, 868428773, 934124083, 999935987, 999998491},
			{136492, 57984889, 123839533, 192782593, 262948151, 332314897, 400868263, 468602179, 535908767, 603242063, 669676999, 736005103, 802289503, 868442791, 934035211, 999948127, 999995473},
			{134856, 57835937, 123458813, 192226379, 262384229, 331726789, 400437959, 468297059, 535627753, 602905729, 669602581, 735717083, 802201837, 868400933, 933944119, 999960541, 999997321},
			{133692, 57644459, 123036587, 191626009, 261723379, 331126007, 399957293, 468032137, 535261691, 602471501, 669339493, 735488063, 802037683, 868188721, 933793097, 999909583, 999998431},
			{132979, 57518099, 122721083, 191163913, 261273091, 330615947, 399644351, 467593219, 535013461, 602266573, 669213899, 735367667, 801934927, 868024319, 933867349, 999983977, 999998833},
			{130173, 57334553, 122201353, 190444003, 260352493, 329862191, 398888543, 466919177, 534591403, 602057923, 669068861, 735412201, 801829999, 868179649, 934088861, 999965261, 999987977},
			{127880, 57179123, 121757309, 189711001, 259508209, 329264393, 398088563, 466449931, 534140813, 601582987, 668804657, 735389173, 801851899, 868133863, 934181221, 999978589, 999999101},
			{127225, 57079753, 121487777, 189227827, 258809429, 328677809, 397558541, 466034183, 533861681, 601273801, 668571467, 735121967, 801626171, 868045499, 934048163, 999944089, 999992957},
			{126179, 56808541, 121113871, 188602441, 257966017, 327959771, 397038667, 465704777, 533444383, 600914837, 668223541, 734811487, 801430489, 867758341, 933897269, 999905503, 999994789},
			{124781, 56688889, 120855281, 188153461, 257189927, 327232013, 396500947, 465144307, 533087651, 600601321, 667920397, 734794127, 801159823, 867734479, 934023121, 999908209, 999990487},
			{123427, 56529679, 120530441, 187519109, 256724653, 326631373, 396025391, 464757773, 532774481, 600198089, 667831259, 734622227, 801174637, 867852257, 934212239, 999954079, 999998891},
			{122825, 56403791, 120319847, 187067627, 256095857, 326116909, 395490181, 464465531, 532348909, 599991683, 667561747, 734490641, 801081103, 867694039, 934013491, 999972037, 999998477},
			{120375, 56149063, 119879471, 186342259, 255075749, 324937187, 394608839, 463603453, 531732589, 599452541, 667247869, 734351353, 800586667, 867229819, 933839321, 999995221},
			{119449, 56003471, 119523673, 185969869, 254390197, 324236287, 393978119, 463079509, 531257471, 599064437, 666910669, 734048759, 800473291, 867155083, 933700121, 999974359, 999998731},
			{118193, 55881011, 119230627, 185372347, 253776163, 323550077, 393341749, 462511891, 530880191, 598636523, 666272647, 733959313, 800454827, 867041863, 933799649, 999900389, 999982349},
			{116615, 55751953, 118885859, 184890179, 252906691, 322781993, 392617217, 461755913, 530303113, 598210447, 665960233, 733585639, 800427451, 867024649, 933624611, 999952319, 999993779},
			{114737, 55602619, 118546811, 184412771, 251951317, 321611089, 391552663, 460969889, 529648261, 597660683, 665375881, 732970507, 799997323, 866886743, 933400427, 999975799, 999989819},
			{113267, 55420403, 118168321, 183782017, 251263219, 320522593, 390654037, 460240969, 529205399, 597167303, 664900909, 732721013, 799963991, 866587303, 933417643, 999974309, 999988489},
			{111517, 55296133, 117738407, 183272381, 250609607, 319524319, 389674273, 459341059, 528275903, 596523383, 664425743, 732361177, 799594867, 866332447, 933235397, 999954283, 999994547},
			{110109, 55097149, 117412681, 182711821, 249954959, 318804767, 388602583, 458489093, 527509019, 595974971, 663796801, 731822191, 799444823, 865992949, 932983091, 999884539, 999997951},
			{109053, 55002121, 117126803, 182302231, 249455293, 318080219, 387759199, 457581847, 527073179, 595479671, 663694117, 731707721, 799491163, 866059291, 933018841, 999968129, 999990119},
			{108016, 54905483, 116866199, 181972099, 248841253, 317549039, 386938183, 456732299, 526257419, 594984419, 663202987, 731482153, 799038577, 866123519, 933085787, 999994847, 999999281},
			{107296, 54801451, 116665117, 181641953, 248407933, 317009123, 386224031, 456232463, 525856021, 594613241, 662828071, 731179613, 799043747, 866090581, 933225089, 999988097, 999995527},
			{105997, 54648017, 116309623, 181236577, 247748141, 316117679, 385340353, 455291497, 525075919, 593944121, 662133419, 730408331, 798666721, 865945807, 933044653, 999934721, 999993299},
			{105007, 54539579, 116057941, 180829889, 247230901, 315424489, 384481057, 454432399, 524250509, 593308591, 661791353, 730063669, 798120997, 865546987, 932626271, 999938711, 999988673},
			{104332, 54459443, 115838659, 180488653, 246888947, 314986271, 383817199, 453646559, 523618987, 592895861, 661308239, 729912389, 797822507, 865411483, 932724977, 999921277, 999986723},
			{103125, 54432127, 115658369, 180074423, 246286861, 314291069, 382956617, 452671081, 522919231, 592267651, 661083923, 729349591, 797498371, 865437983, 932737787, 999982223},
			{102470, 54333397, 115485427, 179652157, 245994109, 313704271, 382365223, 452106829, 522272039, 591705991, 660600389, 728848559, 797212679, 865117637, 932665469, 999953573, 999998407},
			{100544, 54138517, 115192403, 179177077, 245462939, 312675887, 381231457, 450846329, 521175797, 590971679, 659700389, 728145779, 796808381, 865005079, 932240063, 999893731, 999992893},
			{99157, 53997547, 114929791, 178780649, 244741963, 311986447, 380389769, 449841943, 520115027, 589587923, 658816937, 727480081, 796447679, 864455689, 932179967, 999934531, 999987133},
			{97595, 53914187, 114584333, 178407961, 244310719, 311286211, 379688779, 448790323, 518984017, 588735997, 658264693, 726973063, 796045483, 864391421, 932245487, 999923191, 999986293},
			{97270, 53860943, 114457241, 178222927, 244053419, 311009579, 379303889, 448264841, 518344973, 588236953, 657859681, 726742777, 795820513, 864333793, 932276129, 999842161, 999978409},
			{96019, 53791099, 114338207, 177957497, 243576743, 310396291, 378601687, 447412981, 517317847, 587518273, 657237593, 726413411, 795326509, 864108247, 932023009, 999934487, 999991957},
			{95690, 53714741, 114098251, 177679939, 243251641, 309967313, 378034351, 446833859, 516565003, 586783363, 656710381, 725901637, 795027053, 863768951, 931972607, 999926071, 999983681},
			{95125, 53653279, 113953157, 177355939, 242834491, 309540311, 377415509, 446018467, 515665099, 586006411, 656020231, 725388991, 794727979, 863322667, 931750301, 999886831, 999992687},
			{94826, 53575783, 113854031, 177179683, 242516489, 309194617, 377064847, 445515377, 515071793, 585569953, 655623769, 725208371, 794481269, 863251793, 931748747, 999899179, 999992027},
			{93659, 53441783, 113762527, 176838547, 242216783, 308489393, 376456783, 444880589, 514390061, 584790551, 655110497, 724881737, 793968353, 863034833, 931782493, 999838817, 999959633},
			{92064, 53381593, 113507857, 176667389, 241502213, 307991857, 375609167, 444062417, 513418141, 583696811, 653953303, 723981191, 793326679, 862704581, 931777109, 999841391, 999996637},
			{91586, 53297687, 113433377, 176513719, 241349197, 307542163, 375145751, 443590913, 512882791, 583057379, 653593621, 723519679, 792988099, 862646773, 931808387, 999924461, 999999877},
			{91304, 53260577, 113301241, 176324353, 241123877, 307239389, 374710403, 443382299, 512327357, 582694919, 653069353, 723064109, 792777113, 862387037, 931570897, 999849371, 999962759},
			{90799, 53192731, 113150519, 176068397, 240777863, 306781829, 374349551, 442745753, 511794383, 581811299, 652247633, 722278357, 792328067, 862122329, 931469557, 999939977, 999995209},
			{89320, 53082179, 112974263, 175688533, 240379561, 306368549, 373874747, 441945733, 510928799, 580802897, 650926063, 721463173, 791640713, 861548137, 931064419, 999886117, 999989603},
			{88876, 53007127, 112934509, 175573609, 240078667, 306137809, 373546643, 441448837, 510573859, 580430111, 650453753, 721159289, 791288651, 861370439, 931066349, 999988741, 999990503},
			{88616, 52991479, 112782941, 175362917, 239873131, 305843827, 373253813, 441078809, 510021683, 579785747, 649737007, 720495329, 790737979, 860825221, 930719969, 999871231, 999996617},
			{88174, 52917533, 112740361, 175268539, 239703767, 305568839, 372865529, 440751187, 509688827, 579284621, 649245859, 719934437, 790459807, 860774071, 930518881, 999967433, 999997591},
			{86241, 52851797, 112465279, 174912229, 239408999, 304990541, 372244591, 440046469, 508670089, 578064659, 648159433, 718745801, 789355751, 859989283, 929928053, 999919429, 999988361},
			{85839, 52866241, 112441997, 174823633, 239275061, 304823333, 372064243, 439645867, 508440943, 577755289, 647638099, 718249709, 789010723, 859593181, 929967931, 999861673, 999976459},
			{85072, 52830553, 112310071, 174594377, 238948271, 304482161, 371604083, 439339897, 507910163, 577096159, 647206669, 717712349, 788541517, 859157477, 929749543, 999907841, 999985037},
			{84163, 52835017, 112289159, 174459697, 238722343, 304240997, 371110417, 438931133, 507417013, 576385973, 646527331, 716906513, 787839379, 858729511, 929788721, 999801877, 999991393},
			{83433, 52862729, 112236671, 174327913, 238604239, 304109909, 370758719, 438769927, 507037873, 576019813, 646118657, 716652269, 787247723, 858405377, 929514307, 999946723, 999986077},
			{83060, 52811743, 112151203, 174336247, 238465397, 303885599, 370497107, 438531407, 506603347, 575740499, 645722669, 716244973, 786650593, 857728087, 929245969, 999941417, 999997877},
			{82547, 52849229, 112123853, 174288721, 238233949, 303817487, 370609397, 438490357, 506397833, 575598911, 645504557, 715982191, 786427627, 857823851, 929256061, 999974233, 999993173},
			{82046, 52835273, 112113779, 174228413, 238136593, 303798293, 370613123, 438085523, 506141159, 575384233, 645383989, 715843091, 786140183, 857460797, 928899583, 999833279, 999980041},
			{80911, 52779827, 112029851, 174060967, 238161463, 303791753, 370389043, 437748329, 506063011, 575145491, 645088601, 715089731, 785881867, 857024057, 928988197, 999993073, 999996941},
			{80586, 52825313, 112017473, 174155647, 238138721, 303784147, 370243271, 437580179, 505928869, 575126213, 644890621, 714907489, 785800199, 856770589, 928622647, 999903757, 999993089},
			{80110, 52770701, 112021843, 174188353, 238068521, 303640853, 370104209, 437523071, 505713763, 574623527, 644803391, 714795671, 785524609, 856554463, 928371779, 999851053, 999999557},
			{79629, 52780219, 111966649, 174001813, 237953827, 303551383, 369899951, 437089967, 505671911, 574737491, 644416463, 714484703, 785089661, 856123207, 927756383, 999847637, 999999019},
			{79021, 52787597, 111980027, 174175187, 237989641, 303437263, 369937327, 437275777, 505611173, 574650179, 644416579, 714567487, 785162363, 856330037, 927771227, 999967559, 999979451},
			{78568, 52798129, 111900289, 173967527, 237956981, 303449911, 369770351, 437183503, 505382291, 574488349, 644250433, 714233851, 784994929, 856124897, 927328643, 999848429, 999993991}
	};
	
	static final int mayor = 1000000000;
	
	static int solucionMemoria(int n, int p)
	{
		
		int[] primos = criba(31650);
		int indice = -1;
		for(int i = 0; i < primos.length; i++)
			if(primos[i] == p)
			{
				indice = i;
				break;
			}
		if(indice == -1)
		{
			if(n == 1)
				return p;
			else
				return 0;
			
		}
		int tam = memoria[indice][0];
		if(n > tam)
			return 0;
		int i = 0;
		int nMemoria = 1;
		int cuenta = 0;
		while(cuenta + tam / 15 < n)
		{
			cuenta += tam / 15;
			i = memoria[indice][nMemoria++];
		}
		if(cuenta == n)
			return i;
		for(i += p; i <= mayor; i += p)
		{
			boolean paila = false;
			for(int j = 0; j < indice; j++)
				if(i % primos[j] == 0)
				{
					paila = true;
					break;
				}
			if(!paila)
			{
				cuenta++;
				if(cuenta == n)
					return i;
			}
		}
		return 0;
	}
	
	static int solucion(int n, int p)
	{
		
		int[] primos = criba(31650);
		int indice = -1;
		for(int i = 0; i < primos.length; i++)
			if(primos[i] == p)
			{
				indice = i;
				break;
			}
		if(indice == -1)
		{
			if(n == 1)
				return p;
			else
				return 0;
			
		}
		if(mayor < ((long) n) * p)
			return 0;
		int cuenta = 1;
		if(cuenta == n)
			return (int) p;
		for(long i = ((long) p) * p; i <= mayor; i += p)
		{
			boolean paila = false;
			for(int j = 0; j < indice; j++)
				if(i % primos[j] == 0)
				{
					paila = true;
					break;
				}
			if(!paila)
			{
				cuenta++;
				if(cuenta == n)
					return (int) i;
			}
		}
		return 0;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int p = sc.nextInt();
		if(p < 1000)
			System.out.println(solucionMemoria(n, p));
		else
			System.out.println(solucion(n, p));
	}

}
