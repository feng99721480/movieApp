package com.wiseweb.json;

import java.util.List;

import com.wiseweb.json.MoviePlanResult.MoviePlan;

public class SeatResult {
	
	private String action;
	private String status;
	private CommonSeat comSeat;
	private LoversSeat lovSeat;
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

	public CommonSeat getComSeat() {
		return comSeat;
	}

	public void setComSeat(CommonSeat comSeat) {
		this.comSeat = comSeat;
	}

	public LoversSeat getLovSeat() {
		return lovSeat;
	}

	public void setLovSeat(LoversSeat lovSeat) {
		this.lovSeat = lovSeat;
	}

	public List<SeatResult> getSeats() {
		return seats;
	}

	public void setSeats(List<SeatResult> seats) {
		this.seats = seats;
	}

	public static class CommonSeat {
		
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

	}

	public static class LoversSeat {
		
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

	}
}
