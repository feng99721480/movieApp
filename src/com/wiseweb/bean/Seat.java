package com.wiseweb.bean;


public class Seat
{
	/**序号，当为走道时 为"Z"*/
  private String n = null;
  /**可用标签*/
  private String seatState = null;
  /**情侣座*/
  private String loveInd = null;

  
  public void setN(String paramString)
  {
    this.n = paramString;
  }

  public boolean a()
  {
    return ("1".equals(this.loveInd)) || ("2".equals(this.loveInd));
  }

  public String getN()
  {
    return this.n;
  }

  public void setSeatState(String paramString)
  {
    this.seatState = paramString;
  }

  public String getSeatState()
  {
    return this.seatState;
  }

  public void setLoveInd(String paramString)
  {
    this.loveInd = paramString;
  }

  public String getLoveInd()
  {
    return this.loveInd;
  }
}