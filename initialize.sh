#! /bin/bash
# shellcheck disable=SC2154

source config.dat

OLD_PACKAGE_NAME=com.oddblogger.springbootboilerplate
echo "Project Name: $project_name"
echo "Group ID: $group_id"
echo "Artifact ID: $artifact_id"

package="$group_id.$artifact_id"
package=${package/-/}

echo "Package Name:" "$package"
echo "Continue (y/n)"
read -r
if [ "$REPLY" != "y" ]
then
  echo "Exiting..."
  exit
fi

cp -rf . ../"$project_name"
cd ../"$project_name" || exit
project_root=$(pwd)
echo "Removing redundant files ..."
rm -rf target
rm -rf .idea
rm -rf .git
rm -rf initialize.sh
rm -rf config.dat
echo "Updating package name to: $package"
find . -type f -exec sed -i '' "s/${OLD_PACKAGE_NAME}/${package}/g" "{}" \;


IFS='. ' read -r -a array <<< "$group_id"
cd "src/main/java/" || exit
for element in "${array[@]}"
do
  echo "Creating directory: $element"
  mkdir "$element"
  cd "$element" || exit
done
echo "Creating directory: ${artifact_id/-/}"
mkdir "${artifact_id/-/}"
cd "${artifact_id/-/}" || exit
echo "Moving files to new package directory ..."
mv -f "${project_root}"/src/main/java/com/oddblogger/springbootboilerplate/* .
cd "$project_root" || exit
echo "Removing old package directory ..."
rm -rf src/main/java/com/oddblogger
echo "Updating pom.xml ..."
sed -i '' "s/com.oddblogger/$group_id/g" pom.xml
sed -i '' "s/spring-boot-boilerplate/$artifact_id/g" pom.xml
echo "Updating logback.xml ..."
sed -i '' "s/com.oddblogger/$group_id/g" src/main/resources/logback.xml
