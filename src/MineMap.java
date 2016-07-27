import java.util.Random;

public class MineMap {
	
	private int mapHeight, mapWidth;	//maps height and weight, can be defined by users.
	private int mineNum;				//the number of mines in the map
	private int map[][];				//the map
	
	private Random rnd;
	
	MineMap(int height, int width, int num){
		rnd = new Random();
		resetMap(height, width, num);
		
	}
	
	public int getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	public int getMineNum() {
		return mineNum;
	}

	public void setMineNum(int mineNum) {
		this.mineNum = mineNum;
	}

	public int[][] getMap() {
		return map;
	}

	public void resetMap(int height, int width, int num){
		this.mapHeight = height;
		this.mapWidth = width;
		this.mineNum = num;
		map = new int[mapHeight+2][mapWidth+2];
		
		randomMap();
	}
	
	
	public void randomMap(){
		rnd.setSeed(System.currentTimeMillis());
		int count = 0;
		int row, col;
		while(count < mineNum){
			row = rnd.nextInt(mapHeight - 1) + 1;
			col = rnd.nextInt(mapWidth - 1) + 1;
			
			if(map[row][col] != 'X'){
				map[row][col] = 'X';
				setHint(row, col);
				count++;
			}
			
		}

	}
	
	public void setHint(int row, int col){
		for(int i=row-1; i<=row+1; i++){
			for(int j=col-1; j<=col+1; j++){
				if(map[i][j] != 'X'){
					map[i][j]++;
				}
			}
		}
	}
	
	public void prettyPrint(){
		for(int i=0; i<=mapWidth; i++){
			System.out.print(i + "\t");
		}
		
		for(int i=1; i<=mapHeight; i++){
			System.out.println();
			System.out.print(i + "\t");
			for(int j=1; j<=mapWidth; j++){
				if(map[i][j] != 'X'){
					System.out.print(map[i][j] + "\t");
				}else{
					System.out.print((char)map[i][j] + "\t");
				}
			}
		}
		System.out.println();
	}
	
	
}
