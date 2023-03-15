package io.shmilyhe.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SimpleOutputStream extends OutputStream{
	ThreadPoolExecutor executor;
	LinkedBlockingQueue<Runnable> queue;
	private void init() {
		queue=new LinkedBlockingQueue<Runnable>();
		executor= new ThreadPoolExecutor(1, 1,0L, TimeUnit.MILLISECONDS,queue);
	}
	
	OutputStream out;
	public SimpleOutputStream(OutputStream out) {
		this.out=out;
		init();
	}
	
	public SimpleOutputStream(File file){
		try {
			out= new FileOutputStream(file);
			init();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}

	@Override
	public void close() throws IOException {
		try {
		queue.clear();
		executor.shutdown();
		}finally {
			if(out!=null)out.close();
		}
		
	}

	@Override
	public void write(byte[] b) throws IOException {
		executor.submit(new Writer(b));
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		executor.submit(new Writer(b,off,len));
	}
	
	class Writer implements Runnable{
		byte[] b;
		int off;
		int len;
		boolean full=true;
		public Writer(byte[] b,int off,int len) {
			this.b=b;
			this.off=off;
			this.len=len;
			full=false;
		}
		
		public Writer(byte[] b) {
			this.b=b;
			full=true;
		}
		

		@Override
		public void run() {
			try {
				//System.out.println(new String(b));
				if(full) {
					out.write(b);
				}else {
					out.write(b, off, len);	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	

}
