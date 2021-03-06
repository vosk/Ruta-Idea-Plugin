# Ruta Support for IDEA IntelliJ
This Idea plugin adds support for UIMA Ruta in IntelliJ.

## Features

### Since version 0.0.2
* Syntax highlighting for .ruta files
* Package and file path error detection and quickfixes

### Not available - planned for next revisions
* Doc comment support
* Variable and type indexing: "go to declaration" for all elements 


### Not available - planned for 0.1.0
* Generate XMI output for stuff found in resource files 
* Multi-module support, allow many TypeSystems to co-exist

### Not available - planned for 0.2.0
* UIMA TypeSystem generation: Run configurations to allow generation of the TypeSystem xml files
* Multi-module support/ Multi-TypeSystem support

### Not available - don't wait for it soon
* External TypeSystem integration: import some other TypeSystem directly from XML.
* Ruta WorkBench
* Live Testing in side-by-side view editor
* UIMA Ruta TextRuler framework support.
  
## License 

See ./LICENSE 
## Based on
There is an ANTLR4-to-Idea adaptor and a sample usage project.
However, the ruta parser has been defined in ANTLR 3 and I don't plan to rewrite it.
So I maed a fing.  

https://github.com/antlr/antlr4-intellij-adaptor

and

https://github.com/antlr/jetbrains-plugin-sample


## For Developers

This software uses Gradle.
There is a  `downloadRuta` Gradle Task that downloads the ruta ANTLR3 parser and lexer definition from GitHub at:

https://github.com/apache/uima-ruta

Then the Gradle ANTLR plugin generates a **DebugParser** to be used by this plugin.

Since the actual ruta-core is a dependency, and that cannot be used (AFAIK) for our purposes,
the downloaded files are moved to a different package, to coexist happily with the real thing.


## Roadmap

1. Get a syntax analyzer working
    * Get parser to build - {done} 
    * Get the IDEA PSI framework to play nice  -{WIP for error handling} 
    * Get state recovery working for lexer
    * Get state recovery working for parser (fingers crossed)
    * Error handling messages
    * Highlighting - {Basic Support Done}
    * Reference Resolution - {Packages Done}
    * Code Renames 
    * Code completion
    * Get it working for complex projects with multiple scripts
    * Code Scopes  
2. Get a generate UIMA xml action working
    * Simple configuration
    * More complex stuff later

3. Get a  ruta workbench 
    * Live annotation on demand
    * Replicate the functionality of the Eclipse Ruta WorkBench.