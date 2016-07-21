import java.io.*;

public class MineSweeper {
	static short height;
	static short width;
	static short num;
	static ConsoleGame console;
	static boolean start = true;		//the status that this game needs to start

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		
		System.out.println("======================================================================================================");
		System.out.println(" How to play:");
		System.out.println(" >To start game, please input the HEIGHT and WIDTH of map and the NUM of MINES in this map sequencely.");
		System.out.println(" >The range for WIDTH and HEIGHT is 1 ~ 99");
		System.out.println("  Eg. 4 3 2. It indicates that system will create a 4x3 map with 2 mines.");
		System.out.println(" >After input, type Return on key board, the map will be printed on screen. "
				+ "				\n X represents unsweeped block. * represents mine. And numbers represent how many mines in 3x3 area.");
		System.out.println("  Eg. XX1\n     X*X\n     XXX");
		System.out.println(" >Each time input the row and col number of the block that you want to sweep.");
		System.out.println(" >When only the blocks with mine left in the map, WIN the game. /n Enjoy!/n/n");
		System.out.println("======================================================================================================");
		
		while(start){
			start = false;
			
			while(input.length() == 0 || !isValidMap()){
				
				System.out.println("Please input height and width of map and number of mines here:");
				input = buffer.readLine();
				
				String temp[] = input.split(" ");
				
				if (temp.length > 3){
					System.out.println("ERROR: Input too many values. Please input again!");
					input = "";
					continue;
				}else if(temp.length < 3){
					System.out.println("ERROR: Input less values. Please input again!");
					input = "";
					continue;
				}
				
				height = Short.parseShort(temp[0]);
				width = Short.parseShort(temp[1]);
				num = Short.parseShort(temp[2]);
			}
			
			System.out.println("\n======================================================================================================\n");
			
			console = new ConsoleGame(height, width, num);
			console.prettyPrint();
			
			
			
			
			while(!console.getMine()){
				input = "";
				short row = 0;
				short col = 0;
				while(input.length() == 0 || !isValidAction(row, col)){
					
					System.out.println("Please input the row and colomn of map that you want to sweep:");
					input = buffer.readLine();
					
					String temp[] = input.split(" ");
					
					if (temp.length > 2){
						System.out.println("ERROR: Input too many values. Please input again!");
						input = "";
						continue;
					}else if(temp.length < 2){
						System.out.println("ERROR: Input less values. Please input again!");
						input = "";
						continue;
					}
					row = Short.parseShort(temp[0]);
					col = Short.parseShort(temp[1]);
				}
				
				console.sweepMine(row, col);
				console.prettyPrint();
				
				if(console.isWin()){
					System.out.println("Congratulations! You win!");
					break;
				}
			}
			if(console.getMine()){
				System.out.println("Sorry. You failed.");
			}
			
			MineMap map = new MineMap(height, width, num);
			
			map.prettyPrint();
		}

	}
	
	public static boolean isValidMap(){
		boolean flag = true;
		if(height<0 || height>99){
			System.out.println("ERROR: The value of height is not valid. Please input height in 0 and 99!");
			flag = false;
		}
		if(width<0 || width>99){
			System.out.println("ERROR: The value of width is not valid. Please input height in 0 and 99!");
			flag = false;
		}
		if(num > width * height && flag){
			System.out.println("ERROR: Too many mines that the map cannot afford. Please input less Mine numbers!");
			flag = false;
		}
		return flag;
	}
	

	public static boolean isValidAction(short row, short col){
		boolean flag = true;
		if(row<0 || row>height){
			System.out.println("ERROR: The value of row is not valid. Please input row in 0 and " + height + "!");
			flag = false;
		}
		if(col<0 || col>width){
			System.out.println("ERROR: The value of width is not valid. Please input height in 0 and " + width + "99!");
			flag = false;
		}
		
		return flag;
	}


}
