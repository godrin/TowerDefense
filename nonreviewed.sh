#/bin/bash
for a in $(find . -name "*.java") ; { grep review1 $a >/dev/null || echo $a ; }
