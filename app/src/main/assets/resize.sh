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
     convert "$file[x100]" drawable-mdpi/$file
     convert "$file[x150]" drawable-hdpi/$file
     convert "$file[x200]" drawable-xhdpi/$file
     convert "$file[x300]" drawable-xxhdpi/$file
     # convert "$file[x]" drawable-xxxhdpi/$file
done
