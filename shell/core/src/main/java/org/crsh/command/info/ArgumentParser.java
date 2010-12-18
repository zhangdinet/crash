/*
 * Copyright (C) 2010 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.crsh.command.info;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ArgumentParser<T> {

  /** . */
  final CommandInfo<T> command;

  /** . */
  final Pattern optionPattern;

  public ArgumentParser(CommandInfo<T> command) {

    StringBuilder re = new StringBuilder("^(");
    ArrayList<OptionInfo> list = new ArrayList<OptionInfo>();
    int index = 0;
    for (OptionInfo option : command.getOptions()) {
      if (index > 0) {
        re.append('|');
      }
      re.append("(?:\\s*\\-([");
      for (Character opt : option.getOpts()) {
        re.append(opt);
      }
      re.append("])");

      //
      for (int i = 0;i < option.getArity();i++) {
        re.append("\\s*").append("([A-Aa-z0-9]*)");
      }

      //
      re.append(')');
      list.add(option);
      index++;
    }
    re.append(").*");

    //
    String regex = re.toString();
    System.out.println("regex = " + regex);

    //
    this.command = command;
    this.optionPattern = Pattern.compile(regex);
  }

  public MatchIterator parse(String s) {
    return new MatchIterator(this, s);
  }

}
