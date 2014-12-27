import java.util.Random;


public class TepeTirmanma {
	static int[][] tahta = new int[9][9];
	static int[][] tahtaIlk = new int[9][9];
	int kontrol = 0;
	int at = AnaEkran.at;		//at=1
	int vezir = AnaEkran.vezir;	//vezir=2
	int[][] dolu = new int[at+vezir][2]; // tahtaya yerlestirilen elemanlarin lokasyon (satir,sutun) bilgilerini tutan dizi
	static String sonuc = ""; // TextArea'da gorunecek olan bilgilendirmeler
	int adimSayisi = 0; // iterasyon sayisi
	long sure;
	
	public int[][] bul() {
		
		int tehditOnce = 0;
		int tehditSonra = 0;
		for (int i=0;i<9;i++)
			for (int j=0;j<9;j++)
				tahta[i][j] = 0; // islemleri gerceklestirdigimiz tahtayi temsil eden matris
		
		yerlestir(tahta); // at ve vezirlerin tahtaya random olarak yerlestirildigi metod
		for (int i=0;i<9;i++)
			for (int j=0;j<9;j++)
				tahtaIlk[i][j] = tahta[i][j];
		
		long startTime = System.nanoTime(); // algoritmanin calismasi suresini hesaplamada kullanilacak 
		tehditOnce = tehditHesapla(tahta); // ilk tahta tehditleri hesaplaniyor
		sonuc = "";
		sonuc += "Ilk tahta tehdit: "+tehditOnce;
		sonuc += "\nx  y  Tehdit  yeniX  yeniY";
		for (int i=0;i<at+vezir;i++) { // tahtaya yerlestirilmesi istenen elemanlar kadar islem yapilacak
			if(tahta[dolu[i][0]][dolu[i][1]]==1)
				sonuc += "\n"+i+". eleman at yer degistiriliyor";
			else
				sonuc += "\n"+i+". eleman vezir yer degistiriliyor";
			for (int k=0;k<9;k++)
				for (int l=0;l<9;l++)
					if (tahta[k][l] == 0) { // baktigimiz noktada herhangi bir eleman yok ise islemler gerceklesiyor
						tahta[k][l] = tahta[dolu[i][0]][dolu[i][1]]; // tahtaya yerlestirilmis ilk eleman alinip tahtada gezdiriliyor
						tahta[dolu[i][0]][dolu[i][1]] = 0;
						tehditSonra = tehditHesapla(tahta); // tahtanin anlik durumunun tehdit sayisi hesaplaniyor
						if (tehditSonra <= tehditOnce) { // mevcut durumumuzu iyilestirmiyorsa
							tahta[dolu[i][0]][dolu[i][1]] = tahta[k][l]; // eski duruma donuluyor
							tahta[k][l] = 0;
							if(kontrol==1) { //lokal maximuma takýlma kontrolu yapiliyor
								l = 9;
								k = 9;
								kontrol = 0;
							}
						}
						else { // mevcut durumumuzu iyilestiriyorsa
							kontrol = 1;
							sonuc += "\n" +dolu[i][0]+ "  "+dolu[i][1]+ "  " +tehditSonra+ "       " +k+ "        " +l;
							dolu[i][0] = k;
							dolu[i][1] = l;
							tehditOnce = tehditHesapla(tahta);
							adimSayisi++;
						}
					}
		}
		long endTime = System.nanoTime(); // algoritmanin calisma suresini hesaplamada kullanilacak 
		System.out.println(endTime);
		//sure = (endTime - startTime) / 1000000; // nanosaniye anlasilacak bir formata donusturuluyor
		setSure( (endTime - startTime) / 1000000 );
		tehditSonra = tehditHesapla(tahta);
		sonuc += "\n\nSonuc tahtasinda tehdit\nedilen kare sayisi: " +tehditSonra;
		sonuc += "\n\nCozume gidilen adim sayisi: " +adimSayisi;
		
		tehditSonra = tehditHesapla(tahta);
		return tahta;
	}
	
	public long getSure() {
		return sure;
		
	}
	public void setSure(long sure) {
		
		this.sure = sure;
	}

	public void yerlestir(int[][] tahta) {
		Random rnd = new Random();
		int yerX;
		int yerY;
		int k = 0;
		for(int i=0;i<at;i++) { // istenen at sayisinin random olarak yerlestirilecegi noktalar belirleniyor
			yerX = rnd.nextInt(9);
			yerY = rnd.nextInt(9);
			while(tahta[yerX][yerY]!=0) { // daha onceden bir eleman yerlestiyse bos yer bulanada kadar random satir,sutun uret
				yerX = rnd.nextInt(9);
				yerY = rnd.nextInt(9);
			}
			tahta[yerX][yerY] = 1; // atlari 1 ile ifade ettik
			dolu[k][0] = yerX; // tahtaya yerlestirilen elemanlarin lokasyon (satir,sutun) bilgilerini tutan diziye atamalar yapiliyor
			dolu[k][1] = yerY;
			k++;
		}
		for(int i=0;i<vezir;i++) { // istenen vezir sayisinin random olarak yerlestirilecegi noktalar belirleniyor
			yerX = rnd.nextInt(9);
			yerY = rnd.nextInt(9);
			while(tahta[yerX][yerY]!=0) { // daha onceden bir eleman yerlestiyse bos yer bulanada kadar random satir,sutun uret
				yerX = rnd.nextInt(9);
				yerY = rnd.nextInt(9);
			}
			tahta[yerX][yerY] = 2; // vezirleri 2 ile ifade ettik
			dolu[k][0] = yerX;  // tahtaya yerlestirilen elemanlarin lokasyon (satir,sutun) bilgilerini tutan diziye atamalar yapiliyor
			dolu[k][1] = yerY;
			k++;
		}
	}
	
	public int tehditHesapla(int[][] tahta) {
		int tehdit = 0;
		int k = 0 , l = 0;
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++) {
				if(tahta[i][j]==2) {
					//vezirlerin tehdit ettigi kare sayisi
					k = 1;
					l = 1;
					while(i+k<9) {
						tehdit++;
						k++;
					}
					k = 1;
					l = 1;
					while(j+l<9) {
						tehdit++;
						l++;
					}
					k = 1;
					l = 1;
					while(i-k>-1) {
						tehdit++;
						k++;
					}
					k = 1;
					l = 1;
					while(j-l>-1) {
						tehdit++;
						l++;
					}
					k = 1;
					l = 1;
					while(i+k<9 && j+l<9) {
						tehdit++;
						k++;
						l++;
					}
					k = 1;
					l = 1;
					while(i+k<9 && j-l>-1) {
						tehdit++;
						k++;
						l++;
					}
					k = 1;
					l = 1;;
					while(i-k>-1 && j+l<9) {
						tehdit++;
						k++;
						l++;
					}
					k = 1;
					l = 1;
					while(i-k>-1 && j-l>-1) {
						tehdit++;
						k++;
						l++;
					}
					tehdit++;	//kendi karesi +1 alindi
				}
				
				if(tahta[i][j]==1) {
					//atlarin tehdit ettigi kare sayisi
					if(i+1<9 && j+2<9)
						tehdit++;
					if(i-1>-1 && j+2<9)
						tehdit++;
					if(i+1<9 && j-2>-1)
						tehdit++;
					if(i-1>-1 && j-2>-1)
						tehdit++;
					if(i+2<9 && j+1<9)
						tehdit++;
					if(i+2<9 && j-1>-1)
						tehdit++;
					if(i-2>-1 && j-1>-1)
						tehdit++;
					if(i-2>-1 && j+1<9)
						tehdit++;
					tehdit++;	//kendi karesi +1 alindi
				}
			}
		return tehdit;
	}

}
