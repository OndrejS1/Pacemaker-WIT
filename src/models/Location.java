package models;

import com.google.common.base.Objects;

import utils.ToJsonString;

public class Location
{
  static Long   counter = 0l;
  
  public Long  id;
  public float latitude;
  public float longitude;
  
  public Location()
  {
  }
  
  public Location (float latitude, float longitude)
  {
    this.id        = counter++;
    this.latitude  = latitude;
    this.longitude = longitude;
  }
  
  @Override
  public String toString()
  {
    return new ToJsonString(getClass(), this).toString();
  }
  
  @Override  
  public int hashCode()  
  {  
     return Objects.hashCode(this.id, this.latitude, this.longitude);  
  } 
  
  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof Location)
    {
      final Location other = (Location) obj;
      return Objects.equal(latitude, other.latitude) 
          && Objects.equal(longitude, other.longitude);
    }
    else
    {
      return false;
    }
  }
}