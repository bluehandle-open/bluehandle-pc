REM**************************************************************************
REM* Copyright (c) 2011 cnfree.
REM* All rights reserved. This program and the accompanying materials
REM* are made available under the terms of the Eclipse Public License v1.0
REM* which accompanies this distribution, and is available at
REM* http://www.eclipse.org/legal/epl-v10.html
REM*
REM* Contributors:
REM* cnfree - initial API and implementation
REM**************************************************************************
Set ws =createobject("wscript.shell") 
ws.run chr(34)&left(wscript.scriptfullname,instrrev(wscript.scriptfullname,"\"))&"Run.bat"&chr(34),vbhide