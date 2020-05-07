package pfc.mundus4d.complutum;

public class Recorrido_Tracking {
	
	int indice;
	boolean sentido; //True es en el sentido de las agujas del reloj y false al contrario.
	boolean reset = false;
	double AzAnt = 27; 
	float Es =0; 
	float Tx = 0;
	float Ty = 0;
	float Tz = 0;
	float Rx = 0;
	float Ry = 0;
	float Rz = 0;
	float Xmap = 0;
	float Ymap = 0;
	String Map = ""; //prueba_portico_sur3_cal
	
	
	public void Maps_FM(int idEdif, double Az, float X, float Y){
		switch(idEdif){
		case 0: //Fachada Monumental 
		if(Az == 40){
		String Fachada_Monumental_40[] = {"Cr22"};
		
		double[][] Fachada_Monumental_40_Parametros ={{0,	0,	41.21,	1425,	-1140.7,	-94,	1.5760323146,	-0.0436332313,	0.4799655443}};
		
		 Seleccion_Map(Fachada_Monumental_40, Fachada_Monumental_40_Parametros, Az, X, Y);
		 
		}else if(Az == 90){
			
			String Fachada_Monumental_90[] ={"Fachada_Mon2"};
			
			double[][] Fachada_Monumental_90_Parametros ={{0,	0,	46.41,	2075,	619.3,	-24,	1.5638150098,	-0.0436332313,	1.343903524}};
			
			Seleccion_Map(Fachada_Monumental_90, Fachada_Monumental_90_Parametros, Az, X, Y);
			
		}
	break;
	
	}//end switch	
	};
	
	public void Maps_CR (int idEdif, double Az, float X, float Y){
		switch(idEdif){
		case 1: //Criptoportico ext
			if(Az == 40){
				String Criptoportico_40[] = {"Cr22"};
				
				double[][] Criptoportico_40_Parametros ={{0,	0,	49.01,	1670,	-1320,	-94,	1.5638150098,	-0.0436332313,	0.3926990817}};
				
				 Seleccion_Map(Criptoportico_40, Criptoportico_40_Parametros, Az, X, Y);
				 
				}else if(Az == 90){
					
					String Criptoportico_90[] ={"Fachada_Mon2"};
					
					double[][] Criptoportico_90_Parametros ={{0,	0,	46.41,	2075,	619.3,	-24,	1.5638150098,	-0.0436332313,	1.343903524}};
					
					Seleccion_Map(Criptoportico_90, Criptoportico_90_Parametros, Az, X, Y);
					
				}else if(Az == 377){
					String Criptoportico_377[] ={"Cripto00", "Criptoportico_377_2"};
					
					double[][] Criptoportico_377_Parametros ={{0,	0,	47.83,	71.2,	-2175.6,	38,	1.5533430343,	-0.0523598776,	-0.6492624817},
															  {0,	0,	66.03,	835,	-2580,	21,	1.5847589608,	-0.013962634,	-0.4799655443}};
					
					Seleccion_Map(Criptoportico_377, Criptoportico_377_Parametros, Az, X, Y);
					
				}

		break;
		case 9: //Criptoportico int 
			if(Az == 40){
				String Criptoportico_int_40[] = {"Cr22"};
				
				double[][] Criptoportico_int_40_Parametros ={{0,	0,	49.01,	1670,	-1320,	-94,	1.5638150098,	-0.0436332313,	0.3926990817}};
				
				 Seleccion_Map(Criptoportico_int_40, Criptoportico_int_40_Parametros, Az, X, Y);
				 
				}else if(Az == 90){
					
					String Criptoportico_int_90[] ={"Fachada_Mon2"};
					
					double[][] Criptoportico_int_90_Parametros ={{0,	0,	46.41,	2075,	619.3,	-24,	1.5638150098,	-0.0436332313,	1.343903524}};
					
					Seleccion_Map(Criptoportico_int_90, Criptoportico_int_90_Parametros, Az, X, Y);
					
				}else if(Az == 377){
					String Criptoportico_int_377[] ={"Cripto00", "Criptoportico_377_2"};
					
					double[][] Criptoportico_int_377_Parametros ={{0,	0,	47.83,	71.2,	-2175.6,	38,	1.5533430343,	-0.0523598776,	-0.6492624817},
																  {0,	0,	66.03,	835,	-2580,	21,	1.5847589608,	-0.013962634,	-0.4799655443}};
					
					Seleccion_Map(Criptoportico_int_377, Criptoportico_int_377_Parametros, Az, X, Y);
					
				}
		break;
		}		
	};
	
	public void Maps_CU (int idEdif, double Az, float X, float Y){
		switch(idEdif){
		case 2: //Curia ext
			if(Az == 77){
				
				String Curia_77[] = {"Cu_23"};
				
				double[][] Curia_77_Parametros = {{0,	0,	33.99,	1330,	-210,	-102,	1.5358897418,	0.3054326191,	1.1868238914}};
				
				Seleccion_Map(Curia_77, Curia_77_Parametros, Az, X, Y);
				
			}else if(Az == 155){
				
				String Curia_155[] = {"Cutn"};
				
				double[][] Curia_155_Parametros = {{0,	0,	25.31,	774.9,	275,	242.6,	1.5062191445,	-0.0087266463,	1.8256143976}};
				
				Seleccion_Map(Curia_155, Curia_155_Parametros, Az, X, Y);
				
			}else if(Az == 230){
				
				String Curia_230[] = {"Foro", "Foro_Corregido", "Basilica_300", "Foro_f2"};
				
				double[][] Curia_230_Parametros = {{0,	0,	12.8,	-120.3,	-75,	2.8,	1.5673056683,	0.0593411946,	-2.9530970944},
												   {0,	0,	18.61,	96.8,	-10,	-119.3,	1.5533430343,	0.0191986218,	3.1101767271},
												   {0,	0,	22.45,	-134,	193.5,	-60.7,	1.5358897418,	0.0645771823,	-3.0019663134},
												   {0,	0,	15.84,	-74.4,	-45,	-4,		1.5550883635,	0.0436332313,	3.1415926536}};
				
				Seleccion_Map(Curia_230, Curia_230_Parametros, Az, X, Y);
				
			}else if(Az == 360){
				
				String Curia_360[] = {"Hypo"};
				
				double[][] Curia_360_Parametros = {{0,	0,	33.99,	-119,	-1620,	-48,	1.5777776438,	-0.0296705973,	-0.5410520681}};
			
				Seleccion_Map(Curia_360, Curia_360_Parametros, Az, X, Y);
			}else if(Az == 10){
				
				String Curia_10[] = {"Cu2"};
				
				double[][] Curia_10_Parametros = {{0,	0,	27.19,	545,	-765,	-52,	1.5987215948,	-0.0610865238,	0.1570796327}};
				
				Seleccion_Map(Curia_10, Curia_10_Parametros, Az, X, Y);
				
			}
		break;
		case 10: //Curia int
			if(Az == 77){
				
				String Curia_int_77[] = {"Cu_23"};
				
				double[][] Curia_int_77_Parametros = {{0,	0,	33.99,	1330,	-210,	-102,	1.5358897418,	0.3054326191,	1.1868238914}};
				
				Seleccion_Map(Curia_int_77, Curia_int_77_Parametros, Az, X, Y);
				
			}else if(Az == 155){
				
				String Curia_int_155[] = {"Cutn"};
				
				double[][] Curia_int_155_Parametros = {{0,	0,	25.31,	774.9,	275,	242.6,	1.5062191445,	-0.0087266463,	1.8256143976}};
				
				Seleccion_Map(Curia_int_155, Curia_int_155_Parametros, Az, X, Y);
				
			}else if(Az == 230){
				
				String Curia_int_230[] = {"Foro", "Foro_Corregido", "Basilica_300", "Foro_f2"};
				
				double[][] Curia_int_230_Parametros = {{0,	0,	12.8,	-120.3,	-75,	2.8,	1.5673056683,	0.0593411946,	-2.9530970944},
													   {0,	0,	18.61,	96.8,	-10,	-119.3,	1.5533430343,	0.0191986218,	3.1101767271},
													   {0,	0,	22.45,	-134,	193.5,	-60.7,	1.5358897418,	0.0645771823,	-3.0019663134},
													   {0,	0,	15.84,	-74.4,	-45,	-4,		1.5550883635,	0.0436332313,	3.1415926536}};
				
				Seleccion_Map(Curia_int_230, Curia_int_230_Parametros, Az, X, Y);
				
			}else if(Az == 360){
				
				String Curia_int_360[] = {"Hypo"};
				
				double[][] Curia_int_360_Parametros = {{0,	0,	33.99,	-119,	-1620,	-48,	1.5777776438,	-0.0296705973,	-0.5410520681}};
			
				Seleccion_Map(Curia_int_360, Curia_int_360_Parametros, Az, X, Y);
			}else if(Az == 10){
				
				String Curia_int_10[] = {"Cu2"};
				
				double[][] Curia_int_10_Parametros = {{0,	0,	27.69,	555,	-780,	-52,	1.5987215948,	-0.0558505361,	0.1570796327}};
				
				Seleccion_Map(Curia_int_10, Curia_int_10_Parametros, Az, X, Y);
				
			}

		break;		
		}
		
	};
	
	public void Maps_DEC (int idEdif, double Az, float X, float Y){
		switch(idEdif){
		case 3: //Decumano
			if(Az == 190){
				
				String Decumano_190[] = {"Paredon", "Dec_Corregido"};
				
				
				double[][] Decumano_190_Parametros = {{0,	0,	12.8,	225.7,	-35,	-16,	1.5603243513,	0.0174532925,	2.6092672317},
													  {0,	0,	43.77,	817.8,	1794.1,	-91.1,	1.5393804003,	0.0506145483,	2.5778513052}};
				
				Seleccion_Map(Decumano_190, Decumano_190_Parametros, Az, X, Y);
				
			}else if(Az == 230){
				
				String Decumano_230[] = {"Foro", "Foro_Corregido", "Basilica_300", "Foro_f2"};
				
				double[][] Decumano_230_Parametros = {{0,	0,	12.7,	-118.3,	-75,	10.4,	1.5638150098,	0.0436332313,	-2.9530970944},
													  {0,	0,	18.61,	96.8,	-10,	-119.3,	1.5533430343,	0.0191986218,	3.1101767271},
													  {467447.582,	4480468.5,	22.45,	-134,	193.5,	-40.6,	1.5323990833,	0.0331612558,	-3.0037116427},
													  {0,	0,	15.84,	-79.4,	-55,	-3,		1.5550883635,	0.0436332313,	3.1415926536}};
				
				Seleccion_Map(Decumano_230, Decumano_230_Parametros, Az, X, Y);
				
			}else if(Az == 105){
				
				String Decumano_105[] = {"Cudecumano"};
				
				double[][] Decumano_105_Parametros = {{0,	0,	34,	1826,	-425,	127,	1.5620696805,	-0.0087266463,	1.1344640138}};
				
				Seleccion_Map(Decumano_105, Decumano_105_Parametros, Az, X, Y);
				
			}
		break;
		
		}
		
	};
	
	public void Maps_BAS (int idEdif, double Az, float X, float Y){
		switch(idEdif){
		case 4: //Basilica ext
			if(Az == 190){
				
				String Basilica_190[] = {"Paredon", "Dec_Corregido"};
				
				double[][] Basilica_190_Parametros = {{0,	0,	12.8,	224.7,	-35,	-18,	1.5603243513,	0.0174532925,	2.6092672317},
													  {0,	0,	43.77,	817.8,	1794.1,	-91.1,	1.5393804003,	0.0506145483,	2.5778513052}};
				
				Seleccion_Map(Basilica_190, Basilica_190_Parametros, Az, X, Y);
				
			}else if(Az == 230){
				
				String Basilica_230[] = {"Foro", "Foro_Corregido", "Basilica_300", "Foro_f2"};
				
				double[][] Basilica_230_Parametros = {{0,	0,	12.8,	-118.4,	-76.4,	-6.3,	1.5899949486,	0.034906585,	-2.9530970944},
													  {0,	0,	18.61,	96.8,	-10,	-119.3,	1.5533430343,	0.0191986218,	3.1101767271},
													  {467447.582,	4480468.5,	22.45,	-134,	193.5,	-40.6,	1.5323990833,	0.0331612558,	-3.0037116427},
													  {0,	0,	15.84,	-79.4,	-55,	-3,	1.5550883635,	0.0436332313,	3.1415926536}};
			
				Seleccion_Map(Basilica_230, Basilica_230_Parametros, Az, X, Y);
				
			}
		break;
		case 11: //Basilica int
			if(Az == 190){
				
				String Basilica_int_190[] = {"Paredon", "Dec_Corregido"};
				
				double[][] Basilica_int_190_Parametros = {{0,	0,	12.8,	227.7,	-35,	-16,	1.5603243513,	0.0174532925,	2.6092672317},
														  {0,	0,	43.77,	817.8,	1794.1,	-91.1,	1.5393804003,	0.0506145483,	2.5778513052}};
				
				Seleccion_Map(Basilica_int_190, Basilica_int_190_Parametros, Az, X, Y);
				
			}else if(Az == 230){
				
				String Basilica_int_230[] = {"Foro", "Foro_Corregido", "Basilica_300", "Foro_f2"};
				
				double[][] Basilica_int_230_Parametros = {{0,	0,	12.7,	-118.3,	-75,	10.4,	1.5638150098,	0.0436332313,	-2.9530970944},
														  {0,	0,	18.61,	98.8,	-5,		-115.3,	1.5533430343,	0.0191986218,	3.1101767271},
														  {467447.582,	4480468.5,	22.45,	-134,	193.5,	-40.6,	1.5323990833,	0.0331612558,	-3.0037116427},
														  {0,	0,	15.84,	-74.4,	-45,	-4,		1.5550883635,	0.0436332313,	3.1415926536}};
			
				Seleccion_Map(Basilica_int_230, Basilica_int_230_Parametros, Az, X, Y);			
			}
			
		break;
		}
		
	};
	
	
 	public void Maps_PS (int idEdif, double Az, float X, float Y){
		switch(idEdif){
		case 5: //Portico Sur
			if(Az == 205){
				
				String Portico_Sur_205[] = {"Ps00"};
				
				double[][] Portico_Sur_205_Parametros = {{0,	0,	19.95,	440,	-170,	-9,	1.5917402778,	0.0471238898,	-3.1276300196}};
				
				Seleccion_Map(Portico_Sur_205, Portico_Sur_205_Parametros, Az, X, Y);
			}else if(Az == 247){
				
				String Portico_Sur_247[] = {"Ps22", "Portico_Corregido"};
				
				double[][] Portico_Sur_247_Parametros = {{0,	0,	18.35,	190,	290,	51,	1.5917402778,	0.0994837674,	-2.6563911215},
														 {0,	0,	14.04,	272.7,	-349.3,	52.3,	1.5899949486,	0.1029744259,	-3.0927234345}};
				
				Seleccion_Map(Portico_Sur_247, Portico_Sur_247_Parametros, Az, X, Y);
				
			}
		break;	
		}
	};
	
 	public void Maps_TS (int idEdif, double Az, float X, float Y){
		switch(idEdif){
		case 6: //Termas Sur
			
			if(Az == 245){
				
			    String Termas_Sur_245[] = {"Termas_Sur", "Termas_f"};


			    double[][] Termas_Sur_245_Parametros ={{0,	0,	28.5,	-300,	85,	9,	1.6144295581,	-0.013962634,	-2.6005405855},
			    									   {0,	0,	21.94,	-240,	-55,	11,	1.5830136316,	0.034906585,	-2.5185101106}};

			    Seleccion_Map(Termas_Sur_245, Termas_Sur_245_Parametros, Az, X, Y);
				
				
		    }else if(Az == 281){
				
				String Termas_Sur_281[] ={"Termas_Sur_22"};
				
												//Posición(X,Y), Escala, Traslación(X,Y,Z), Rotación(Xrad,Yrad,Zrad)
				double[][] Termas_Sur_281_Parametros = {{0,	0,	46.5,	-340.6,	-635.8,	-1,	1.4922565105,	-0.0872664626,	-0.5235987756}};//end [][]
				
				Seleccion_Map(Termas_Sur_281, Termas_Sur_281_Parametros, Az, X, Y);
				

				
			}else if(Az == 10){
				
				String Termas_Sur_10[] = {"Termas_Sur_3"};
				
				double[][] Termas_Sur_10_Parametros ={{0,	0,	35.11,	115,	-792.5,	209,	1.4835298642,	-0.0087266463,	-0.1343903524}};
				
				Seleccion_Map(Termas_Sur_10, Termas_Sur_10_Parametros, Az, X, Y);
				
			}
			break;
	case 12: //Termas Sur int
		if(Az == 245){
			
		    String Termas_Sur_245[] = {"Termas_Sur", "Termas_f"};


		    double[][] Termas_Sur_245_Parametros ={{0,	0,	28.5,	-340,	0,	4,	1.6144295581,	-0.013962634,	-2.6075219025},
		    									   {0,	0,	22.54,	-220,	-35,	1,	1.5847589608,	0.034906585,	-2.5481807079}};

		    Seleccion_Map(Termas_Sur_245, Termas_Sur_245_Parametros, Az, X, Y);
			
			
	    }else if(Az == 281){
			
			String Termas_Sur_281[] ={"Termas_Sur_22"};
			
											//Posición(X,Y), Escala, Traslación(X,Y,Z), Rotación(Xrad,Yrad,Zrad)
			double[][] Termas_Sur_281_Parametros = {{0,	0,	46.5,	-335.8,	-645.6,	9,	1.4922565105,	-0.0872664626,	-0.5235987756}};
			
			Seleccion_Map(Termas_Sur_281, Termas_Sur_281_Parametros, Az, X, Y);
			

			
		}else if(Az == 10){
			
			String Termas_Sur_10[] = {"Termas_Sur_3"};
			
			double[][] Termas_Sur_10_Parametros ={{0,	0,	36.51,	110,	-762.5,	192,	1.5044738152,	-0.0261799388,	-0.1570796327}};
			
			Seleccion_Map(Termas_Sur_10, Termas_Sur_10_Parametros, Az, X, Y);
			
		}
	
	break;
		}
	};
	
	
 	public void Maps_MER (int idEdif, double Az, float X, float Y){
		switch(idEdif){
		case 7: //Mercado SI
			if(Az == 95){
				
				String Mercado_SI_95[] ={"Mercado_i2", "Mercado_i2_Corregido"};
				
				double[][] Mercado_SI_95_Parametros = {{0,	0,	53.14,	179.1,	-154.6,	13.7,	1.551597705,	0.0488692191,	1.8919369092},
													   {0,	0,	53.14,	187.8,	-219.4,	50.7,	1.5760323146,	0.0191986218,	2.0141099568}};
				
				Seleccion_Map(Mercado_SI_95, Mercado_SI_95_Parametros, Az, X, Y);
				
			}else if(Az == 153){
				
				String Mercado_SI_153[] ={"Mercado_c2"};
				
				double[][] Mercado_SI_153_Parametros = {{0,	0,	70,	-506.8,	-45,	-164,	1.551597705,	-0.0034906585,	2.7872908154}};
				
				Seleccion_Map(Mercado_SI_153, Mercado_SI_153_Parametros, Az, X, Y);
				
			}else if(Az == 182){
				String Mercado_SI_182[] ={"Mercado_d", "Mercado_d_Corregido"};
				
				double[][] Mercado_SI_182_Parametros = {{0,	0,	109.6,	-1388,	-190,	36,	1.5690509975,	-0.0052359878,	3.3807027611},
														{0,	0,	52.6,	-558,	-225,	86,	1.5393804003,	-0.0575958653,	3.1590459461}};
			
				Seleccion_Map(Mercado_SI_182, Mercado_SI_182_Parametros, Az, X, Y);
			
			}
		break;
		case 13: //Mercado SIII
			if(Az == 95){
				
				String Mercado_SIII_95[] ={"Mercado_i2", "Mercado_i2_Corregido"};
				
				double[][] Mercado_SIII_95_Parametros = {{0,	0,	53.18,	188,	-120,	13,	1.558579022,	0.0453785606,	1.8430676901},
														 {0,	0,	53.14,	187.8,	-219.4,	50.7,	1.5760323146,	0.0191986218,	2.0141099568}};
				
				Seleccion_Map(Mercado_SIII_95, Mercado_SIII_95_Parametros, Az, X, Y);
				
			}else if(Az == 153){
				
				String Mercado_SIII_153[] ={"Mercado_c2"};
				
				double[][] Mercado_SIII_153_Parametros = {{0,	0,	70,	-506.8,	-45,	-164,	1.551597705,	0.006981317,	2.7872908154}};
				
				Seleccion_Map(Mercado_SIII_153, Mercado_SIII_153_Parametros, Az, X, Y);
				
			}else if(Az == 182){
				String Mercado_SI_182[] ={"Mercado_d", "Mercado_d_Corregido"};
				
				double[][] Mercado_SI_182_Parametros = {{0,	0,	109.6,	-1388,	-190,	36,	1.5690509975,	-0.0052359878,	3.3807027611},
													    {0,	0,	52.6,	-558,	-225,	86,	1.5393804003,	-0.0575958653,	3.1590459461}};
			
				Seleccion_Map(Mercado_SI_182, Mercado_SI_182_Parametros, Az, X, Y);
			
			}

		break;		
		}
	};
	
 	public void Maps_NI (int idEdif, double Az, float X, float Y){
 		switch(idEdif){
 		
 		case 8:
 		if(Az == 100){
			String Ninfeo_100[] ={"Crninfeo"};
		
			double[][] Ninfeo_100_Parametros = {{0,	0,	67.27,	1876.3,	572.9,	-125.6,	1.5882496193,	0,	1.0995574288}};
			
			Seleccion_Map(Ninfeo_100, Ninfeo_100_Parametros, Az, X, Y);
		}else if(Az == 360){
			
			String Ninfeo_360[] ={"Cuninfeo"};
			
			double[][] Ninfeo_360_Parametros = {{0,	0,	61.77,	151.7,	-2314.8,	129.4,	1.5882496193,	-0.0523598776,	-0.8185594192}};
			
			Seleccion_Map(Ninfeo_360, Ninfeo_360_Parametros, Az, X, Y);
			
		}
 		break;
 		case 14:
 	 		if(Az == 100){
 				String Ninfeo_int_100[] ={"Crninfeo"};
 			
 				double[][] Ninfeo_int_100_Parametros = {{0,	0,	68.17,	1876.3,	562.9,	-105.6,	1.5882496193,	0,	1.0995574288}};
 				
 				Seleccion_Map(Ninfeo_int_100, Ninfeo_int_100_Parametros, Az, X, Y);
 			}else if(Az == 360){
 				
 				String Ninfeo_int_360[] ={"Cuninfeo"};
 				
 				double[][] Ninfeo_int_360_Parametros = {{0,	0,	61.77,	151.7,	-2314.8,	129.4,	1.5882496193,	-0.0523598776,	-0.8185594192}};
 				
 				Seleccion_Map(Ninfeo_int_360, Ninfeo_int_360_Parametros, Az, X, Y);
 				
 			}
 			
 		break;
 		}
	};
	
	
	public void Seleccion_Map(String mid[], double mpar[][], double Az, float Xt, float Yt ){
		
		if(reset == true){
			Map = mid[indice];
			Xmap = (float)mpar[indice][0];
			Ymap = (float)mpar[indice][1];
			Es = (float)mpar[indice][2];
			Tx = (float)mpar[indice][3];
			Ty = (float)mpar[indice][4];
			Tz = (float)mpar[indice][5];
			Rx = (float)mpar[indice][6];
			Ry = (float)mpar[indice][7];
			Rz = (float)mpar[indice][8];
			
		}else{		
		
		if(Az == AzAnt){
			if(sentido == true){//Sentido positivo
				indice++;
				if(indice >= mid.length)indice = 0;
				Map = mid[indice];
				Xmap = (float)mpar[indice][0];
				Ymap = (float)mpar[indice][1];
				Es = (float)mpar[indice][2];
				Tx = (float)mpar[indice][3];
				Ty = (float)mpar[indice][4];
				Tz = (float)mpar[indice][5];
				Rx = (float)mpar[indice][6];
				Ry = (float)mpar[indice][7];
				Rz = (float)mpar[indice][8];
				
			}else{
				indice = indice - 1;
				if(indice < 0)indice = mid.length - 1;
				Map = mid[indice];
				Xmap = (float)mpar[indice][0];
				Ymap = (float)mpar[indice][1];
				Es = (float)mpar[indice][2];
				Tx = (float)mpar[indice][3];
				Ty = (float)mpar[indice][4];
				Tz = (float)mpar[indice][5];
				Rx = (float)mpar[indice][6];
				Ry = (float)mpar[indice][7];
				Rz = (float)mpar[indice][8];
			}//end if sentido
				
		}else {//Giro del teléfono				
			DistanciaMinima(mpar, Xt, Yt );
			Map = mid[indice];
			Xmap = (float)mpar[indice][0];
			Ymap = (float)mpar[indice][1];
			Es = (float)mpar[indice][2];
			Tx = (float)mpar[indice][3];
			Ty = (float)mpar[indice][4];
			Tz = (float)mpar[indice][5];
			Rx = (float)mpar[indice][6];
			Ry = (float)mpar[indice][7];
			Rz = (float)mpar[indice][8];			
		}	//end if AzAnt == AzAct
		}//end reset
		AzAnt = Az;
	}

	public void DistanciaMinima(double m[][], float Xe, float Ye){
		double Xp;
		double Yp;
		double DistX;
		double DistY;
		double Dist;
		double DistMin = 10000000;
		
		for(int i=0; i < m.length;i++){

			Xp = m[i][0];
			Yp = m[i][1];
			DistX = Xp-(double)Xe;
			DistY = Yp-(double)Ye;
			Dist = Math.sqrt((Math.pow(DistX, 2)+ Math.pow(DistY, 2)));
			if(Dist <= DistMin){
				DistMin = Dist;
				indice = i;
			}//end if
		}//end for
		
	}//end DistanciaMinima
	
}//end Recorrido_Tracking

