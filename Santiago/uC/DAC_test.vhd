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
			  clk : in std_logic);
end DAC_test;

architecture DAC_test_b of DAC_test is


	 type arreglo is array(0 to 499) of integer range 0 to 4095;

	 signal contador : integer range 0 to 4095 := 0;

	 signal numeros : arreglo :=
	 (0,  25,  51,  77,  102,  128,  154,  180,  205,  231,  257,  282,  308,  334,  359,  385,  410,  436,  462,  487,  513,  538,  564,  589,  615,  640,  665,  691,  716,  742,  767,  792,  817,  843,  868,  893,  918,  943,  968,  993,  1018,  1043,  1068,  1092,  1117,  1142,  1167,  1191,  1216,  1240,  1265,  1289,  1314,  1338,  1362,  1387,  1411,  1435,  1459,  1483,  1507,  1531,  1555,  1578,  1602,  1626,  1649,  1673,  1696,  1720,  1743,  1766,  1789,  1813,  1836,  1859,  1881,  1904,  1927,  1950,  1972,  1995,  2017,  2040,  2062,  2084,  2106,  2128,  2150,  2172,  2194,  2215,  2237,  2258,  2280,  2301,  2322,  2344,  2365,  2386,  2406,  2427,  2448,  2468,  2489,  2509,  2530,  2550,  2570,  2590,  2610,  2630,  2649,  2669,  2688,  2708,  2727,  2746,  2765,  2784,  2803,  2821,  2840,  2858,  2877,  2895,  2913,  2931,  2949,  2967,  2985,  3002,  3020,  3037,  3054,  3071,  3088,  3105,  3122,  3138,  3155,  3171,  3187,  3203,  3219,  3235,  3251,  3266,  3282,  3297,  3312,  3327,  3342,  3357,  3372,  3386,  3401,  3415,  3429,  3443,  3457,  3471,  3484,  3498,  3511,  3524,  3537,  3550,  3563,  3576,  3588,  3600,  3612,  3625,  3636,  3648,  3660,  3671,  3683,  3694,  3705,  3716,  3726,  3737,  3747,  3758,  3768,  3778,  3788,  3797,  3807,  3816,  3826,  3835,  3844,  3852,  3861,  3870,  3878,  3886,  3894,  3902,  3910,  3917,  3925,  3932,  3939,  3946,  3953,  3959,  3966,  3972,  3978,  3984,  3990,  3996,  4001,  4007,  4012,  4017,  4022,  4027,  4031,  4036,  4040,  4044,  4048,  4052,  4055,  4059,  4062,  4065,  4068,  4071,  4074,  4076,  4079,  4081,  4083,  4085,  4086,  4088,  4089,  4091,  4092,  4092,  4093,  4094,  4094,  4094,  4095,  4094,  4094,  4094,  4093,  4092,  4092,  4091,  4089,  4088,  4086,  4085,  4083,  4081,  4079,  4076,  4074,  4071,  4068,  4065,  4062,  4059,  4055,  4052,  4048,  4044,  4040,  4036,  4031,  4027,  4022,  4017,  4012,  4007,  4001,  3996,  3990,  3984,  3978,  3972,  3966,  3959,  3953,  3946,  3939,  3932,  3925,  3917,  3910,  3902,  3894,  3886,  3878,  3870,  3861,  3852,  3844,  3835,  3826,  3816,  3807,  3797,  3788,  3778,  3768,  3758,  3747,  3737,  3726,  3716,  3705,  3694,  3683,  3671,  3660,  3648,  3636,  3625,  3612,  3600,  3588,  3576,  3563,  3550,  3537,  3524,  3511,  3498,  3484,  3471,  3457,  3443,  3429,  3415,  3401,  3386,  3372,  3357,  3342,  3327,  3312,  3297,  3282,  3266,  3251,  3235,  3219,  3203,  3187,  3171,  3155,  3138,  3122,  3105,  3088,  3071,  3054,  3037,  3020,  3002,  2985,  2967,  2949,  2931,  2913,  2895,  2877,  2858,  2840,  2821,  2803,  2784,  2765,  2746,  2727,  2708,  2688,  2669,  2649,  2630,  2610,  2590,  2570,  2550,  2530,  2509,  2489,  2468,  2448,  2427,  2406,  2386,  2365,  2344,  2322,  2301,  2280,  2258,  2237,  2215,  2194,  2172,  2150,  2128,  2106,  2084,  2062,  2040,  2017,  1995,  1972,  1950,  1927,  1904,  1881,  1859,  1836,  1813,  1789,  1766,  1743,  1720,  1696,  1673,  1649,  1626,  1602,  1578,  1555,  1531,  1507,  1483,  1459,  1435,  1411,  1387,  1362,  1338,  1314,  1289,  1265,  1240,  1216,  1191,  1167,  1142,  1117,  1092,  1068,  1043,  1018,  993,  968,  943,  918,  893,  868,  843,  817,  792,  767,  742,  716,  691,  665,  640,  615,  589,  564,  538,  513,  487,  462,  436,  410,  385,  359,  334,  308,  282,  257,  231,  205,  180,  154,  128,  102,  77,  51,  25);


	 signal spi_clk : std_logic;
	 signal sdata : std_logic_vector(11 downto 0);
	 
	 signal clk_cnt : std_logic_vector(25 downto 0) := "00000000000000000000000000";
	 
	 signal ready : std_logic;
	 
	 signal spi_mosi_1, dac_cs1 : std_logic;
	 
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
	
	leds_out <= spi_clk & spi_mosi_1 & dac_cs1 & ready & "0000";
   
	DAC0 : DAC port map (
	  data => "101010101010",
	  spi_sck => spi_clk,
	  da_conv => '1',
	  addr => "0000",
	  spi_miso => spi_miso,
	  ready => ready,
	  spi_mosi => spi_mosi_1,
	  dac_cs => dac_cs1);
	
	spi_sck <= spi_clk;
	spi_dac_cs <= dac_cs1;
	  
	process(clk)
	begin
		if rising_edge(clk) then
			clk_cnt <= clk_cnt + 1;
		end if;
	end process;
	
	spi_clk <= clk_cnt(25);
	
	
sdata <= conv_std_logic_vector(numeros(contador), 12);

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

