package ClassificadorSmartphone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.aafanasev.fonoapi.DeviceEntity;
import com.aafanasev.fonoapi.retrofit.FonoApiFactory;
import com.aafanasev.fonoapi.retrofit.FonoApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileManager {
	private static String[] brands = FileManager.readFileIntoLines(FileManager.BRANDS_DB).toArray(new String[] {});
	private static final String token = "0cd1d2134743a100b50c2c69153c5757dca4a2a963ab7a64";
	private static FonoApiService fono = new FonoApiFactory().create();
	private static int i = 0;
	
	public static final String OUTPUT_PATH = "files/output.tsv";
	public static final String INPUT_PATH = "files/data_estag_ds.tsv";
	public static final String PHONES_DB = "files/phonesdb.tsv";
	public static final String BRANDS_DB = "files/brands.tsv";
	
	
	//lê o arquivo e coloca cada linha no Array fileLines
	public static List<String> readFileIntoLines(String path) {
			try{
				BufferedReader buffRead = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));

				List<String> linesList = new ArrayList<String>();
				String line;
				line = buffRead.readLine();

				while(line != null) {
					linesList.add(line);
					line = buffRead.readLine();
				}
				buffRead.close();
				return linesList;
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	
	public void appendLinesIntoFile(List<String> lines,String path) throws IOException {
		BufferedWriter buffWrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true), StandardCharsets.UTF_8));
		for(String s : lines) {
			buffWrite.append(s + "\n");
		}
		buffWrite.close(); 
	}
	
	public void writeLinesIntoFile(List<String> lines,String path) throws IOException {
		BufferedWriter buffWrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, false), StandardCharsets.UTF_8));
		for(String s : lines) {
			buffWrite.append(s + "\n");
		}
		buffWrite.close(); 
	}

	//consulta a API e cria (caso ainda não exista) um arquivo com os dados dos smartphones
	public static void createPhonesDB() throws InterruptedException, IOException {
		File temp = new File(FileManager.PHONES_DB);
		if(!temp.exists()) {
			int brandsSize = brands.length;
			FileWriter fw = new FileWriter(FileManager.PHONES_DB, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);

			while(i < brandsSize) {
				char ch = 'a';
				while(ch  <= 'z') {
					fono.getDevice(token, brands[i] + " " + ch, null, null).enqueue(new Callback<List<DeviceEntity>>() {
						@Override
						public void onResponse(Call<List<DeviceEntity>> call, Response<List<DeviceEntity>> response) {
							response.body().forEach(device -> {
								out.println(device.getDeviceName());
								System.out.println(device.getDeviceName());
							});
						}

						@Override
						public void onFailure(Call<List<DeviceEntity>> call, Throwable t) {
						}
					});
					ch++;
				}
				i++;
			}
			out.close();
		}else {System.out.println("Arquivo phonesdb.tsv já existe");}
	}

	public static String[] getBrands() {
		return brands;
	}
}

