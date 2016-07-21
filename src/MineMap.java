import java.util.Random;

public class MineMap {
	
	private short mapHeight, mapWidth;	//maps height and weight, can be defined by users.
	private int mineNum;				//the number of mines in the map
	private short map[][];				//the map
	
	private Random rnd;
	
	MineMap(short height, short width, short num){
		rnd = new Random();
		resetMap(height, width, num);
		
	}
	
	public int getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(short mapHeight) {
		this.mapHeight = mapHeight;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(short mapWidth) {
		this.mapWidth = mapWidth;
	}

	public int getMineNum() {
		return mineNum;
	}

	public void setMineNum(int mineNum) {
		this.mineNum = mineNum;
	}

	public short[][] getMap() {
		return map;
	}

	public void resetMap(short height, short width, short num){
		this.mapHeight = height;
		this.mapWidth = width;
		this.mineNum = num;
		map = new short[mapHeight+2][mapWidth+2];
		
		randomMap();
	}
	
	
	public void randomMap(){
		rnd.setSeed(System.currentTimeMillis());
		int count = 0;
		int row, col;
		while(count < mineNum){
			row = rnd.nextInt(mapHeight - 1) + 1;
			col = rnd.nextInt(mapWidth - 1) + 1;
			
			if(map[row][col] != 'O'){
				map[row][col] = 'O';
				setHint(row, col);
				count++;
			}
			
		}

	}
	
	public void setHint(int row, int col){
		for(int i=row-1; i<=row+1; i++){
			for(int j=col-1; j<=col+1; j++){
				if(map[i][j] != 'O'){
					map[i][j]++;
				}
			}
		}
	}
	
	public void prettyPrint(){
		for(int i=1; i<=mapHeight; i++){
			for(int j=1; j<=mapWidth; j++){
				System.out.print(map[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	
}
