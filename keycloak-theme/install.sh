#!/bin/bash

set -e

install_dir=../src/main/docker/keycloak
jar_filename=music.jar
theme_jar=target/$jar_filename
source_folder=themes

[[ ! -d "$source_folder/theme" ]] && echo "Themes must be based on a 'theme' folder" && exit 1

[[ ! -d target ]] && mkdir "target"
jar cfvM $theme_jar -C $source_folder .

# Expand relative path for logging purposes
echo "Installing to dir $(
  cd "$(dirname "$install_dir")"
  pwd
)/$(basename "$install_dir")"

[[ ! -d $install_dir/deploy ]] && mkdir $install_dir/deploy

[[ -n "$(ls -A $install_dir/deploy)" ]] && rm $install_dir/deploy/*

cp $theme_jar $install_dir/deploy/$jar_filename

echo "Theme $jar_filename installed successfully!!!"
