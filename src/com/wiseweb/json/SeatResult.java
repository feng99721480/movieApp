package com.wiseweb.json;

import java.util.List;

import com.wiseweb.json.MoviePlanResult.MoviePlan;

public class SeatResult {
	
	private String action;
	private String status;
	private AllSeat allSeat;
	private LoverSeat lovSeat;
	private List<SeatResult> seats;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public AllSeat getAllSeat() {
		return allSeat;
	}

	public void setAllSeat(AllSeat allSeat) {
		this.allSeat = allSeat;
	}

	public LoverSeat getLovSeat() {
		return lovSeat;
	}

	public void setLovSeat(LoverSeat lovSeat) {
		this.lovSeat = lovSeat;
	}

	public List<SeatResult> getSeats() {
		return seats;
	}

	public void setSeats(List<SeatResult> seats) {
		this.seats = seats;
	}

	public static class AllSeat {
		
		private String graphCol;// 相对于屏幕的列号
		private String graphRow;// 相对于屏幕排号
		private String hallId;// 厅
		private String seatCol;// 影厅列号
		private String seatNo;// 座位号
		private String seatPieceName;
		private String seatPieceNo;
		private String seatRow;// 影厅排号
		private int seatState;// 座位状态
		private int seatType;// 座位类型
		public AllSeat(){
			
		}
		public AllSeat(String graphCol, String graphRow, String hallId,
				String seatCol, String seatNo, String seatPieceName,
				String seatPieceNo, String seatRow, int seatState, int seatType) {
			super();
			this.graphCol = graphCol;
			this.graphRow = graphRow;
			this.hallId = hallId;
			this.seatCol = seatCol;
			this.seatNo = seatNo;
			this.seatPieceName = seatPieceName;
			this.seatPieceNo = seatPieceNo;
			this.seatRow = seatRow;
			this.seatState = seatState;
			this.seatType = seatType;
		}

		public String getGraphCol() {
			return graphCol;
		}

		public void setGraphCol(String graphCol) {
			this.graphCol = graphCol;
		}

		public String getGraphRow() {
			return graphRow;
		}

		public void setGraphRow(String graphRow) {
			this.graphRow = graphRow;
		}

		public String getHallId() {
			return hallId;
		}

		public void setHallId(String hallId) {
			this.hallId = hallId;
		}

		public String getSeatCol() {
			return seatCol;
		}

		public void setSeatCol(String seatCol) {
			this.seatCol = seatCol;
		}

		public String getSeatNo() {
			return seatNo;
		}

		public void setSeatNo(String seatNo) {
			this.seatNo = seatNo;
		}

		public String getSeatPieceName() {
			return seatPieceName;
		}

		public void setSeatPieceName(String seatPieceName) {
			this.seatPieceName = seatPieceName;
		}

		public String getSeatPieceNo() {
			return seatPieceNo;
		}

		public void setSeatPieceNo(String seatPieceNo) {
			this.seatPieceNo = seatPieceNo;
		}

		public String getSeatRow() {
			return seatRow;
		}

		public void setSeatRow(String seatRow) {
			this.seatRow = seatRow;
		}

		public int getSeatState() {
			return seatState;
		}

		public void setSeatState(int seatState) {
			this.seatState = seatState;
		}

		public int getSeatType() {
			return seatType;
		}

		public void setSeatType(int seatType) {
			this.seatType = seatType;
		}
		@Override
		public String toString() {
			return "AllSeat [graphCol=" + graphCol + ", graphRow=" + graphRow
					+ ", hallId=" + hallId + ", seatCol=" + seatCol
					+ ", seatNo=" + seatNo + ", seatPieceName=" + seatPieceName
					+ ", seatPieceNo=" + seatPieceNo + ", seatRow=" + seatRow
					+ ", seatState=" + seatState + ", seatType=" + seatType
					+ "]";
		}

	}

	public static class LoverSeat {
		
		private String graphCol;// 相对于屏幕的列号
		private String graphRow;// 相对于屏幕排号
		private String hallId;// 厅
		private boolean isLoverL;// 情侣座左右
		private String seatCol;// 影厅列号
		private String seatNo;// 座位号
		private String seatPieceName;
		private String seatPieceNo;
		private String seatRow;// 影厅排号
		private int seatState;// 座位状态
		private int seatType;// 座位类型
		
		public LoverSeat(){
			
		}
		public LoverSeat(String graphCol, String graphRow, String hallId,
				boolean isLoverL, String seatCol, String seatNo,
				String seatPieceName, String seatPieceNo, String seatRow,
				int seatState, int seatType) {
			super();
			this.graphCol = graphCol;
			this.graphRow = graphRow;
			this.hallId = hallId;
			this.isLoverL = isLoverL;
			this.seatCol = seatCol;
			this.seatNo = seatNo;
			this.seatPieceName = seatPieceName;
			this.seatPieceNo = seatPieceNo;
			this.seatRow = seatRow;
			this.seatState = seatState;
			this.seatType = seatType;
		}

		public String getGraphCol() {
			return graphCol;
		}

		public void setGraphCol(String graphCol) {
			this.graphCol = graphCol;
		}

		public String getGraphRow() {
			return graphRow;
		}

		public void setGraphRow(String graphRow) {
			this.graphRow = graphRow;
		}

		public String getHallId() {
			return hallId;
		}

		public void setHallId(String hallId) {
			this.hallId = hallId;
		}

		public boolean isLoverL() {
			return isLoverL;
		}

		public void setLoverL(boolean isLoverL) {
			this.isLoverL = isLoverL;
		}

		public String getSeatCol() {
			return seatCol;
		}

		public void setSeatCol(String seatCol) {
			this.seatCol = seatCol;
		}

		public String getSeatNo() {
			return seatNo;
		}

		public void setSeatNo(String seatNo) {
			this.seatNo = seatNo;
		}

		public String getSeatPieceName() {
			return seatPieceName;
		}

		public void setSeatPieceName(String seatPieceName) {
			this.seatPieceName = seatPieceName;
		}

		public String getSeatPieceNo() {
			return seatPieceNo;
		}

		public void setSeatPieceNo(String seatPieceNo) {
			this.seatPieceNo = seatPieceNo;
		}

		public String getSeatRow() {
			return seatRow;
		}

		public void setSeatRow(String seatRow) {
			this.seatRow = seatRow;
		}

		public int getSeatState() {
			return seatState;
		}

		public void setSeatState(int seatState) {
			this.seatState = seatState;
		}

		public int getSeatType() {
			return seatType;
		}

		public void setSeatType(int seatType) {
			this.seatType = seatType;
		}
		@Override
		public String toString() {
			return "LoverSeat [graphCol=" + graphCol + ", graphRow=" + graphRow
					+ ", hallId=" + hallId + ", isLoverL=" + isLoverL
					+ ", seatCol=" + seatCol + ", seatNo=" + seatNo
					+ ", seatPieceName=" + seatPieceName + ", seatPieceNo="
					+ seatPieceNo + ", seatRow=" + seatRow + ", seatState="
					+ seatState + ", seatType=" + seatType + "]";
		}

	}
}
