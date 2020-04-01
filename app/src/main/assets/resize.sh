#!/bin/bash
rm -rf drawable-mdpi
mkdir -p drawable-mdpi
rm -rf drawable-hdpi
mkdir -p drawable-hdpi
rm -rf drawable-xhdpi
mkdir -p drawable-xhdpi
rm -rf drawable-xxhdpi
mkdir -p drawable-xxhdpi
rm -rf drawable-xxxhdpi
for file in *.png; do
     convert "$file[x50]" drawable-mdpi/ic_$file
     convert "$file[x75]" drawable-hdpi/ic_$file
     convert "$file[x100]" drawable-xhdpi/ic_$file
     convert "$file[x150]" drawable-xxhdpi/ic_$file
     # convert "$file[x]" drawable-xxxhdpi/ic_$file
done
