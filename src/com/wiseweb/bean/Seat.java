package com.wiseweb.bean;


public class Seat
{
	/**序号，当为走道时 为"Z"*/
  private String n = null;
  /**可用标签*/
  private String seatState = null;
  /**情侣座*/
  private boolean loveInd ;
  
  
  public void setN(String paramString)
  {
    this.n = paramString;
  }

  public boolean a()
  {
    return ((true==this.loveInd) || false == this.loveInd);
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

  public void setLoveInd(boolean paramString)
  {
    this.loveInd = paramString;
  }

  public Boolean getLoveInd()
  {
    return this.loveInd;
  }
}