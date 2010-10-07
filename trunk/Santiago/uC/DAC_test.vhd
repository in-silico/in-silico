----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    14:53:01 04/30/2010 
-- Design Name: 
-- Module Name:    DAC_test - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
-- 
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

---- Uncomment the following library declaration if instantiating
---- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity DAC_test is
	Port (  spi_sck : out  STD_LOGIC;
           spi_miso : in  STD_LOGIC;
           spi_mosi : out  STD_LOGIC := '0';
           spi_dac_cs,spi_rom_cs,strataflash_ce,
			  strataflash_oe,platformflash_oe,spi_amp_cs, strataflash_we,
			  spi_adc_conv	: out  STD_LOGIC;
			  leds_out : out STD_LOGIC_VECTOR(7 downto 0);
			  dac_clr : out STD_LOGIC;
			  switch : in STD_LOGIC;
			  clk : in std_logic);
end DAC_test;

architecture DAC_test_b of DAC_test is


	 type arreglo is array(0 to 499) of integer range 0 to 4095;

	 signal contador : integer range 0 to 4095 := 0;

	 signal numeros : arreglo :=
	 (2048,  2073,  2098,  2123,  2148,  2173,  2198,  2223,  2248,  2273,  2298,  2323,  2348,  2373,  2398,  2422,  2447,  2472,  2496,  2520,  2545,  2569,  2593,  2618,  2642,  2666,  2689,  2713,  2737,  2760,  2784,  2807,  2830,  2853,  2876,  2899,  2922,  2944,  2967,  2989,  3011,  3033,  3055,  3076,  3098,  3119,  3140,  3161,  3182,  3203,  3223,  3243,  3263,  3283,  3303,  3322,  3342,  3361,  3380,  3398,  3417,  3435,  3453,  3471,  3488,  3505,  3523,  3539,  3556,  3572,  3589,  3604,  3620,  3635,  3651,  3666,  3680,  3695,  3709,  3723,  3736,  3749,  3763,  3775,  3788,  3800,  3812,  3824,  3835,  3846,  3857,  3868,  3878,  3888,  3898,  3907,  3916,  3925,  3933,  3942,  3950,  3957,  3965,  3972,  3978,  3985,  3991,  3997,  4002,  4007,  4012,  4017,  4021,  4025,  4028,  4032,  4035,  4037,  4040,  4042,  4044,  4045,  4046,  4047,  4047,  4048,  4047,  4047,  4046,  4045,  4044,  4042,  4040,  4037,  4035,  4032,  4028,  4025,  4021,  4017,  4012,  4007,  4002,  3997,  3991,  3985,  3978,  3972,  3965,  3957,  3950,  3942,  3933,  3925,  3916,  3907,  3898,  3888,  3878,  3868,  3857,  3846,  3835,  3824,  3812,  3800,  3788,  3775,  3763,  3749,  3736,  3723,  3709,  3695,  3680,  3666,  3651,  3635,  3620,  3604,  3589,  3572,  3556,  3539,  3523,  3505,  3488,  3471,  3453,  3435,  3417,  3398,  3380,  3361,  3342,  3322,  3303,  3283,  3263,  3243,  3223,  3203,  3182,  3161,  3140,  3119,  3098,  3076,  3055,  3033,  3011,  2989,  2967,  2944,  2922,  2899,  2876,  2853,  2830,  2807,  2784,  2760,  2737,  2713,  2689,  2666,  2642,  2618,  2593,  2569,  2545,  2520,  2496,  2472,  2447,  2422,  2398,  2373,  2348,  2323,  2298,  2273,  2248,  2223,  2198,  2173,  2148,  2123,  2098,  2073,  2048,  2022,  1997,  1972,  1947,  1922,  1897,  1872,  1847,  1822,  1797,  1772,  1747,  1722,  1697,  1673,  1648,  1623,  1599,  1575,  1550,  1526,  1502,  1477,  1453,  1429,  1406,  1382,  1358,  1335,  1311,  1288,  1265,  1242,  1219,  1196,  1173,  1151,  1128,  1106,  1084,  1062,  1040,  1019,  997,  976,  955,  934,  913,  892,  872,  852,  832,  812,  792,  773,  753,  734,  715,  697,  678,  660,  642,  624,  607,  590,  572,  556,  539,  523,  506,  491,  475,  460,  444,  429,  415,  400,  386,  372,  359,  346,  332,  320,  307,  295,  283,  271,  260,  249,  238,  227,  217,  207,  197,  188,  179,  170,  162,  153,  145,  138,  130,  123,  117,  110,  104,  98,  93,  88,  83,  78,  74,  70,  67,  63,  60,  58,  55,  53,  51,  50,  49,  48,  48,  48,  48,  48,  49,  50,  51,  53,  55,  58,  60,  63,  67,  70,  74,  78,  83,  88,  93,  98,  104,  110,  117,  123,  130,  138,  145,  153,  162,  170,  179,  188,  197,  207,  217,  227,  238,  249,  260,  271,  283,  295,  307,  320,  332,  346,  359,  372,  386,  400,  415,  429,  444,  460,  475,  491,  506,  523,  539,  556,  572,  590,  607,  624,  642,  660,  678,  697,  715,  734,  753,  773,  792,  812,  832,  852,  872,  892,  913,  934,  955,  976,  997,  1019,  1040,  1062,  1084,  1106,  1128,  1151,  1173,  1196,  1219,  1242,  1265,  1288,  1311,  1335,  1358,  1382,  1406,  1429,  1453,  1477,  1502,  1526,  1550,  1575,  1599,  1623,  1648,  1673,  1697,  1722,  1747,  1772,  1797,  1822,  1847,  1872,  1897,  1922,  1947,  1972,  1997,  2022);


	 signal numeros_ds : arreglo :=
	 (0,  8,  16,  24,  32,  40,  48,  56,  64,  72,  80,  88,  96,  104,  112,  120,  128,  136,  144,  152,  160,  168,  176,  184,  192,  200,  208,  216,  224,  232,  240,  248,  256,  264,  272,  280,  288,  296,  304,  312,  320,  328,  336,  344,  352,  360,  368,  376,  384,  392,  400,  408,  416,  424,  432,  440,  448,  456,  464,  472,  480,  488,  496,  504,  512,  520,  528,  536,  544,  552,  560,  568,  576,  584,  592,  600,  608,  616,  624,  632,  640,  648,  656,  664,  672,  680,  688,  696,  704,  712,  720,  728,  736,  744,  752,  760,  768,  776,  784,  792,  800,  808,  816,  824,  832,  840,  848,  856,  864,  872,  880,  888,  896,  904,  912,  920,  928,  936,  944,  952,  960,  968,  976,  984,  992,  1000,  1008,  1016,  1024,  1032,  1040,  1048,  1056,  1064,  1072,  1080,  1088,  1096,  1104,  1112,  1120,  1128,  1136,  1144,  1152,  1160,  1168,  1176,  1184,  1192,  1200,  1208,  1216,  1224,  1232,  1240,  1248,  1256,  1264,  1272,  1280,  1288,  1296,  1304,  1312,  1320,  1328,  1336,  1344,  1352,  1360,  1368,  1376,  1384,  1392,  1400,  1408,  1416,  1424,  1432,  1440,  1448,  1456,  1464,  1472,  1480,  1488,  1496,  1504,  1512,  1520,  1528,  1536,  1544,  1552,  1560,  1568,  1576,  1584,  1592,  1600,  1608,  1616,  1624,  1632,  1640,  1648,  1656,  1664,  1672,  1680,  1688,  1696,  1704,  1712,  1720,  1728,  1736,  1744,  1752,  1760,  1768,  1776,  1784,  1792,  1800,  1808,  1816,  1824,  1832,  1840,  1848,  1856,  1864,  1872,  1880,  1888,  1896,  1904,  1912,  1920,  1928,  1936,  1944,  1952,  1960,  1968,  1976,  1984,  1992,  2000,  2008,  2016,  2024,  2032,  2040,  2048,  2056,  2064,  2072,  2080,  2088,  2096,  2104,  2112,  2120,  2128,  2136,  2144,  2152,  2160,  2168,  2176,  2184,  2192,  2200,  2208,  2216,  2224,  2232,  2240,  2248,  2256,  2264,  2272,  2280,  2288,  2296,  2304,  2312,  2320,  2328,  2336,  2344,  2352,  2360,  2368,  2376,  2384,  2392,  2400,  2408,  2416,  2424,  2432,  2440,  2448,  2456,  2464,  2472,  2480,  2488,  2496,  2504,  2512,  2520,  2528,  2536,  2544,  2552,  2560,  2568,  2576,  2584,  2592,  2600,  2608,  2616,  2624,  2632,  2640,  2648,  2656,  2664,  2672,  2680,  2688,  2696,  2704,  2712,  2720,  2728,  2736,  2744,  2752,  2760,  2768,  2776,  2784,  2792,  2800,  2808,  2816,  2824,  2832,  2840,  2848,  2856,  2864,  2872,  2880,  2888,  2896,  2904,  2912,  2920,  2928,  2936,  2944,  2952,  2960,  2968,  2976,  2984,  2992,  3000,  3008,  3016,  3024,  3032,  3040,  3048,  3056,  3064,  3072,  3080,  3088,  3096,  3104,  3112,  3120,  3128,  3136,  3144,  3152,  3160,  3168,  3176,  3184,  3192,  3200,  3208,  3216,  3224,  3232,  3240,  3248,  3256,  3264,  3272,  3280,  3288,  3296,  3304,  3312,  3320,  3328,  3336,  3344,  3352,  3360,  3368,  3376,  3384,  3392,  3400,  3408,  3416,  3424,  3432,  3440,  3448,  3456,  3464,  3472,  3480,  3488,  3496,  3504,  3512,  3520,  3528,  3536,  3544,  3552,  3560,  3568,  3576,  3584,  3592,  3600,  3608,  3616,  3624,  3632,  3640,  3648,  3656,  3664,  3672,  3680,  3688,  3696,  3704,  3712,  3720,  3728,  3736,  3744,  3752,  3760,  3768,  3776,  3784,  3792,  3800,  3808,  3816,  3824,  3832,  3840,  3848,  3856,  3864,  3872,  3880,  3888,  3896,  3904,  3912,  3920,  3928,  3936,  3944,  3952,  3960,  3968,  3976,  3984, 3992);

	 signal spi_clk : std_logic;
	 signal sdata : std_logic_vector(11 downto 0);
	 
	 signal clk_cnt : std_logic_vector(4 downto 0) := "00000";
	 
	 signal ready : std_logic;
	 
	 signal spi_mosi_1, dac_cs1 : std_logic;
	 
	 signal da_conv : std_logic;
	 
	 component DAC is
    Port ( data : in  STD_LOGIC_VECTOR (11 downto 0);
           spi_sck : in  STD_LOGIC;
           da_conv : in  STD_LOGIC;
           addr : in  STD_LOGIC_VECTOR (3 downto 0);
           spi_miso : in  STD_LOGIC;
           ready : out  STD_LOGIC;
           spi_mosi : out  STD_LOGIC;
           dac_cs : out  STD_LOGIC);
	end component;
begin
	   
	spi_rom_cs <= '1';	
	strataflash_ce <= '1';
	strataflash_we <= '1';
	strataflash_oe <= '1';
	platformflash_oe <= '0';
	spi_amp_cs <= '1';
	spi_adc_conv <= '1';
	spi_mosi <= spi_mosi_1;
	dac_clr <= '1';
	
	leds_out <= spi_clk & spi_mosi_1 & dac_cs1 & ready & spi_miso & "00" & da_conv;
   
	DAC0 : DAC port map (
	  data => sdata,
	  spi_sck => spi_clk,
	  da_conv => da_conv,
	  addr => "0000",
	  spi_miso => spi_miso,
	  ready => ready,
	  spi_mosi => spi_mosi_1,
	  dac_cs => dac_cs1);
	
	da_conv <= '1';
	
	spi_sck <= spi_clk;
	spi_dac_cs <= dac_cs1;
	  
	process(clk)
	begin
		if rising_edge(clk) then
			clk_cnt <= clk_cnt + 1;
		end if;
	end process;
	
	spi_clk <= clk_cnt(4);
	
	
sdata <= conv_std_logic_vector(numeros(contador), 12) when switch = '1' else conv_std_logic_vector(numeros_ds(contador), 12);

process(spi_clk)

begin

	if rising_edge(spi_clk) then
		if contador = 4095 then
			contador <= 1;
		else
			contador <= contador + 1;
		end if;
	end if;

end process;


end DAC_test_b;
