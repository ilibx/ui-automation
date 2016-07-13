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

import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.PointerByReference;
import mmarquee.automation.uiautomation.IUIAutomationRangeValuePattern;

/**
 * Created by inpwt on 01/03/2016.
 *
 * Wrapper for the range pattern.
 */
public class Range extends BasePattern {
    private IUIAutomationRangeValuePattern getPattern() {
        Unknown uElement = new Unknown(this.pattern);

        Guid.REFIID refiidElement = new Guid.REFIID(IUIAutomationRangeValuePattern.IID);

        PointerByReference pbr = new PointerByReference();

        WinNT.HRESULT result0 = uElement.QueryInterface(refiidElement, pbr);

        if (COMUtils.SUCCEEDED(result0)) {
            return IUIAutomationRangeValuePattern.Converter.PointerToIUIAutomationRangeValuePattern(pbr);
        } else {
            return null; // or throw exception?
        }
    }

    public void setValue (double value) {
        this.getPattern().Set_Value(value);
    }

    public double getValue () {
        DoubleByReference dbr = new DoubleByReference();

        int result = this.getPattern().Get_CurrentValue(dbr);

        return dbr.getValue();
    }
}
