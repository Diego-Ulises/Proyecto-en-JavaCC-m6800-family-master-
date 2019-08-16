#!/bin/bash

file=""
array=($(git tag -l))
length=${#array[@]}
tags=""

if [ $length -gt 1 ]
then
    tags="${array[length-2]}..${array[length-1]}"
elif [ $length -gt 0 ]
then
    tags="${array[length-1]}"
fi

file+="**Changes**\n\n"
file+=$(git --no-pager log $tags --pretty=format:"\n- [(%h)]($CI_PROJECT_URL/commit/%H) **%cn** - %s" --reverse)

curl https://gitlab.com/api/v4/projects/$CI_PROJECT_ID/releases --request POST --header 'Content-Type: application/json' --header "PRIVATE-TOKEN: $TOKEN_GITLAB" --data @<(cat <<EOF
{
    "id": "$CI_PROJECT_ID",
    "name": "Release $CI_COMMIT_TAG",
    "tag_name": "$CI_COMMIT_TAG",
    "description": "$file",
    "assets": {
        "links": [
            {
                "name": "M6800.jar",
                "url": "https://gitlab.com/api/v4/projects/$CI_PROJECT_ID/jobs/artifacts/$CI_COMMIT_TAG/raw/M6800.jar?job=build"
            }
        ]
    }
}
EOF
)
