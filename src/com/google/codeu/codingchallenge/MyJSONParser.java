// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.codingchallenge;

import java.io.IOException;

final class MyJSONParser implements JSONParser {

  @Override
  public JSON parse(String in) throws IOException {
    in =in.trim();
    return helper(in);
  }

  public JSON helper(String in) throws IOException
  {
    JSON json = new MyJSON();
    JSON json2 = new MyJSON();
    if (in.charAt(0) == '{' && in.charAt(in.length() - 1) == '}')
    {
      // Checks if it is in the valid format
      in = in.substring(1, in.length() - 1).trim();
      if(in.length()==0)
      {
        // Checks if it is an empty object
        return json;
      }
      int count = countOccurrences(in, ':');
      // Finds if there is a possibility for multiple objects or : in any of the strings
      if (count > 0) {
        //Needs to have an object, so checking if it's valid
        String key = findKey(in);
        // Finding the key
        String value = in.substring(key.length()+1).trim();
        // Counting the rest as the value for the main key
        if (countOccurrences(value, '{')>0)
        {
          // Seeing if there are nested objects
          if(value.contains(","))
          {
            // Seeing if there are multiple nested objects
            value = value.substring(1,value.length()-1);
            String[] pairs = value.split(",");
            for(int i = 0;i<pairs.length;i++)
            {
              String[] keys = pairs[i].split(":");
              keys[0] = keys[0].trim();
              keys[1] = keys[1].trim();
              json2.setString(keys[0].substring(1,keys[0].length()-1),keys[1].substring(1,keys[1].length()-1));
            }
            json.setObject(key.substring(1,key.length()-1), json2);
          }
        }
        else
        {
          // Only string, not object, so reacting
          json.setString(key.substring(1,key.length()-1),value.substring(1,value.length()-1));
        }
      }
      return json;
    }
    throw new IOException("Invalid input");
  }
  public String findKey(String in)
  {
    // Finding the key of the function, keeping in mind there may be :,{,or } showing up in the key
    int count1 = countOccurrences(in, ':');
    int count2 = countOccurrences(in, '{');
    if(count1==1)
    {
      return in.substring(0,in.indexOf(':'));
    }
    else if(count2 == 0)
    {
      return in.substring(0,in.lastIndexOf(':'));
    }
    else
    {
      int index=0;
      for(int i = 0; i <count1; i++)
      {
        index = in.indexOf(':',index);
        if(in.charAt(index-1)=='"')
        {
          return in.substring(0,index);
        }
      }
    }
    return "";
  }

  public int countOccurrences(String in, char toFind)
  {
    // Finding the amount of times a char appears in the inputted string
    int count = 0;
    for (int i=0; i < in.length(); i++)
    {
      if (in.charAt(i) == toFind)
      {
        count++;
      }
    }
    return count;
  }
}