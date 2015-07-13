package de.derandroidpro.soundbartutorial;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	

	public ListView lv1;
	public String[] soundnamen = {"Alarm","Böller","Donner","Drucker","Fliege","Glocke","PC Maus","Scherben klirren","Tastatur","Telefonklingeln","Wisch 1","Wisch 2"};
	public int[] soundId = {R.raw.alarm , R.raw.boeller , R.raw.donner , R.raw.drucker , R.raw.fliege , R.raw.glocke , R.raw.pc_maus , R.raw.scherben , R.raw.tastatur , R.raw.telefon , R.raw.wisch1 ,R.raw.wisch2 };
	
	public ArrayAdapter<String> listapdapter;
	
	public MediaPlayer mp1;
	
	
	public String ordnerpfad = Environment.getExternalStorageDirectory() + "/soundbar";
	public String soundpfad = ordnerpfad + "/sound.mp3";
	public File ordnerfile = new File(ordnerpfad);
	public File soundfile = new File(soundpfad);
	public Uri urisound = Uri.parse(soundpfad);
	public byte[] byte1 = new byte [1024];
	public int zwischenspeicher = 0;
	public InputStream is1;
	public FileOutputStream fos;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mp1 = MediaPlayer.create(this,R.raw.alarm );
		
		
		lv1 = (ListView) findViewById(R.id.listView1);
		
		listapdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, soundnamen);
		lv1.setAdapter(listapdapter);
		lv1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view1, int arg2,
					long arg3) {
			
				
				mp1.release();
				mp1 = MediaPlayer.create(MainActivity.this, soundId[lv1.getPositionForView(view1)]);
				
				mp1.start();
	
				
			}
		});
		
		lv1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view2,
					int arg2, long arg3) {
				
				
				if(! ordnerfile.exists()){
					
					try {
						ordnerfile.mkdir();
					} catch (Exception e) {
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "Fehler", Toast.LENGTH_SHORT).show();
					}
					
					
				}
				
				
				try {
					
					is1 = getResources().openRawResource(soundId[lv1.getPositionForView(view2)]);
					fos = new FileOutputStream(soundfile);
					
					while((zwischenspeicher = is1.read(byte1)) >0){
						
						
						fos.write(byte1, 0, zwischenspeicher);
						
					}
					
					is1.close();
					fos.close();
					
					
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "Fehler", Toast.LENGTH_SHORT).show();
				}
				
				
				
				return true;
			}
		});
		
	}


	@Override
	protected void onPause() {
		mp1.stop();
		super.onPause();
	}
	
}
