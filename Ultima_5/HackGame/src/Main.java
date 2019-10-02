import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//saved is the file in tthe game after it has been played and modified 
		String saved = "C:\\Ultima_5\\SAVED.GAM";
		//empty is the file of the game without any modifications 
		String empty = "C:\\empty\\SAVED.GAM";
		Scanner scan= new Scanner(System.in);
		
		//open the files using a random access file class to be able to manipulate them and compare them
		RandomAccessFile f1 = new RandomAccessFile(saved, "rw");
		RandomAccessFile f2 = new RandomAccessFile(empty, "rw");

		int dex=14;
		int str=15;
		int Int=16;
		int magic=17;
		int hm=18;
		int hp=20;
		int ex=22;

		//changes attributes to the main character. 
		writeToRandomAccessFile(saved,dex,99,1,true);   
		writeToRandomAccessFile(saved,str,99, 1,true);  
		writeToRandomAccessFile(saved,Int,99, 1,true);   
		writeToRandomAccessFile(saved,magic,02,1,true); 
		writeToRandomAccessFile(saved,hm,999, 2, false);    
		writeToRandomAccessFile(saved,hp,999, 2, false);    
		writeToRandomAccessFile(saved,ex,9999,2, false );  
		
		
	//prompting the user to enter and manipulate the file wherever they want. 
	System.out.println("enter position ");
	int pos=scan.nextInt();
	System.out.println("Enter value");
	int value=scan.nextInt();
	int bytes=0;
	boolean be=true;
	if(value>=0 && value<=255){
		bytes=1;
		be=true;
	}
	if(value>=256 && value<=65535){
		bytes=2;
		be=false;
	}
	while(value>65535){
		System.out.println("use a value between 0 and 65535");
		System.out.println("enter position ");
		pos=scan.nextInt();
		System.out.println("Enter value");
		value=scan.nextInt();
	}
	writeToRandomAccessFile(saved,pos,value,bytes,be);
	
	}
	
	public static void writeToRandomAccessFile(String file, int position, int  b, int bytes, boolean be) {
        try {
            RandomAccessFile fileStore = new RandomAccessFile(file, "rw");
            ByteBuffer bb= ByteBuffer.allocate(bytes);
            if(be){
            	bb.order(ByteOrder.BIG_ENDIAN);
            }
            else{
            	bb.order(ByteOrder.LITTLE_ENDIAN);
            }
            if(bytes<2){
                bb.put((byte)b);
            }
            else{
            	bb.putShort((short)b);
            }

            // moves file pointer to position specified
            fileStore.seek(position);

            // writing to RandomAccessFile
            fileStore.write(bb.array());
            fileStore.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
