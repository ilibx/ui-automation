/*
 * Copyright 2016 inpwtepydjuf@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmarquee.automation.pattern;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import mmarquee.automation.AutomationElement;
import mmarquee.automation.uiautomation.IUIAutomationElement;
import mmarquee.automation.uiautomation.IUIAutomationElementArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inpwt on 29/02/2016.
 *
 * Base for the pattern wrappers
 */
public abstract class BasePattern implements Pattern {

    /**
     * The underlying automation pattern
     */
    protected Pointer pattern;

    /**
     * Constructs a Pattern
     */
    public BasePattern () {
        this.pattern = null;
    }

    /**
     * Turns a collection (array) of automation elements, it to a collection.
     *
     * @param collection The ElementArray.
     * @return The List
     */
    List<AutomationElement> collectionToList(IUIAutomationElementArray collection) {

        IntByReference ibr = new IntByReference();

        int result = collection.get_Length(ibr);

        List<AutomationElement> list = new ArrayList<AutomationElement>();

        for (int count = 0; count < ibr.getValue(); count++) {

            PointerByReference pbr = new PointerByReference();

            int res = collection.GetElement(count, pbr);

            Unknown uElement = new Unknown(pbr.getValue());

            Guid.REFIID refiidElement = new Guid.REFIID(IUIAutomationElement.IID);

            WinNT.HRESULT result0 = uElement.QueryInterface(refiidElement, pbr);

            if (COMUtils.SUCCEEDED(result0)) {
                IUIAutomationElement element =
                        IUIAutomationElement.Converter.PointerToIUIAutomationElement(pbr);

                list.add(new AutomationElement(element));
            }
        }

        return list;
    }

    /**
     * Sets the pattern
     * @param pattern The pattern to set
     */
    public void setPattern(Pointer pattern) {
        this.pattern =  pattern;
    }

    /**
     * Is this pattern available?
     * @return True if available.
     */
    public boolean isAvailable () {
        return (pattern == null);
    }
}
