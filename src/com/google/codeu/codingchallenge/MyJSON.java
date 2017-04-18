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

import java.util.*;

final class MyJSON implements JSON {

  Map<String, Object> list = new HashMap<String, Object>();

  @Override
  public JSON getObject(String name)
  // Returns the objects in it as long as it's not a String
  {
    if (list.containsKey(name))
    {
      if (!(list.get(name) instanceof String))
      {
        return (JSON)(list.get(name));
      }
    }
    return null;
  }

  @Override
  public JSON setObject(String name, JSON value)
  // Adds new objects
  {
    if (list.containsKey(name))
    {
      list.remove(name);
    }
    list.put(name, value);
    return this;
  }

  @Override
  public String getString(String name)
  // Gets the value if it's a string
  {
    if (list.containsKey(name))
    {
      if (list.get(name) instanceof String)
      {
        return (String)(list.get(name));
      }
    }
    return null;
  }

  @Override
  public JSON setString(String name, String value)
  // Adds new string type object
  {
    if (list.containsKey(name))
    {
      list.remove(name);
    }
    list.put(name, value);
    return this;
  }

  @Override
  public void getObjects(Collection<String> names)
  // Returns list of keys if object
  {
    for (String key : list.keySet())
    {
      if (getObject(key) !=null)
      {
        names.add(key);
      }
    }
  }

  @Override
  public void getStrings(Collection<String> names)
  // Returns list of keys if String
  {
    for (String key : list.keySet())
    {
      if (getString(key) !=null)
      {
        names.add(key);
      }
    }
  }
}
