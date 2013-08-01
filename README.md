ObjectMapper
============

A java library for the automatic mapping of custom classes to and from XML/SOAP. With ObjectMapper, you can build XML and SOAP packets automatically based on your complex objects, potentially saving hundreds of lines of serialization code.

![ScreenShot](https://raw.github.com/uacaps/ObjectMapper/master/ScreenShots/xmlScreen-01.png)

##Download Jar
Here you can Download the latest jar. to add into your project.
[ObjectMapper 1.0](https://github.com/uacaps/ObjectMapper/raw/master/ObjectMapper.jar)

##OMSerailizer

The OMSerailizer is an annotation interface that allows you to map the correct variable name to serialize to for soap and xml.

Ex. 

```
@OMSerailizer("name")
private String _name;
```
Now instead of the ObjectMapper mapping "_name" to the variable name it will map it to "name" and visa versa on the way back to being an object.

--------------------
## License ##

Copyright (c) 2012 The Board of Trustees of The University of Alabama
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. Neither the name of the University nor the names of the contributors
    may be used to endorse or promote products derived from this software
    without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
OF THE POSSIBILITY OF SUCH DAMAGE.
